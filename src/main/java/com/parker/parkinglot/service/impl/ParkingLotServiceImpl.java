/**
 * 
 */
package com.parker.parkinglot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.parker.parkinglot.cache.ParkingManager;
import com.parker.parkinglot.constants.ErrorConstants;
import com.parker.parkinglot.exception.ParkingLotException;
import com.parker.parkinglot.internal.NearestFirstParkingPolicy;
import com.parker.parkinglot.internal.ParkingPolicy;
import com.parker.parkinglot.model.Vehicle;
import com.parker.parkinglot.service.ParkingLotService;

/**
 * @author vikramsingh
 *
 */
public class ParkingLotServiceImpl implements ParkingLotService {

	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private ParkingManager parkingManager = null;

	@Override
	public void createParkingSlots(int capacity, int parkingLevels) throws ParkingLotException {

		if (parkingManager != null) {

			throw new ParkingLotException(ErrorConstants.PARKING_ALREADY_CREATED);

		}

		List<Integer> levels = new ArrayList<>();
		Map<Integer, ParkingPolicy> levelParkingPolicies = new HashMap<Integer, ParkingPolicy>();
		List<Integer> capacities = new ArrayList<>();

		for (int i = 1; i <= parkingLevels; i++) {
			levels.add(i);
			levelParkingPolicies.put(i, new NearestFirstParkingPolicy());

			capacities.add(capacity); // keeping capacity for each level same
		}

		parkingManager = ParkingManager.init(levels, capacities, levelParkingPolicies);

		System.out.println("Created a parking lot with " + capacity + " slots");

	}

	@Override
	public Optional<Integer> park(Vehicle vehicle, int parkingLevel) throws ParkingLotException {
		
		Optional<Integer> allocatedSlot = Optional.empty();
		if (this.parkingManager == null) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_NOT_FOUND);

		}
		readWriteLock.writeLock().lock();

		try {
			allocatedSlot = Optional.of(parkingManager.park(parkingLevel, vehicle));
			if (allocatedSlot.get() == ErrorConstants.PARKING_FULL_CODE) {
				print(ErrorConstants.PARKING_FULL_MESSAGE);
			} else if (allocatedSlot.get() == -2) {
				print("Vehicle already parked.");
			} else {
				print("Allocated slot number: " + allocatedSlot.get());
			}

		} catch (Exception pe) {
			
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_GENERIC_ERROR, pe);
			
		} finally {
			readWriteLock.writeLock().unlock();
		}
		return allocatedSlot;
	}

	@Override
	public void leave(int slotNumber, int parkingLevel) throws ParkingLotException {
		if (this.parkingManager == null) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_NOT_FOUND);

		}
		
		readWriteLock.writeLock().lock();
		try {

			String message = parkingManager.leave(parkingLevel, slotNumber) ? "Slot number " + slotNumber + " is free"
					: "Slot number is already empty";
			print(message);

		} catch (Exception e) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_GENERIC_ERROR, e);
		} finally {
			readWriteLock.writeLock().unlock();
		}

	}

	@Override
	public void getStatus(int parkingLevel) throws ParkingLotException {
		if (this.parkingManager == null) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_NOT_FOUND);

		}
		
		readWriteLock.readLock().lock();
		try {
			List<String> statusList = parkingManager.getStatus(parkingLevel);
			if (statusList.isEmpty()) {
				print("Sorry, parking lot is empty.");
			} else {
				print("Slot No.\tRegistration No\tColour");
				for (String status : statusList) {
					print(status);
				}
			}
		} catch (Exception e) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_GENERIC_ERROR, e);
		} finally {
			readWriteLock.readLock().unlock();
		}

	}

	@Override
	public void getRegistrationNoProvidedColour(String colour, int parkingLevel) throws ParkingLotException {
		if (this.parkingManager == null) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_NOT_FOUND);

		}
		readWriteLock.readLock().lock();
		try {
			List<String> registrations = parkingManager.getRegistrationNoProvidedColour(parkingLevel, colour);
			if (registrations.isEmpty()) {
				print("Not Found");
			} else {
				print(String.join(",", registrations));
			}

		} catch (Exception e) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_GENERIC_ERROR, e);
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	@Override
	public void getSlotNoProvidedColour(String colour, int parkingLevel) throws ParkingLotException {
		if (this.parkingManager == null) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_NOT_FOUND);

		}
		readWriteLock.readLock().lock();
		try {
			List<Integer> slots = parkingManager.getSlotNoProvidedColour(parkingLevel, colour);
			if (slots.isEmpty()) {
				print("Not Found");
			} else {
				StringJoiner joiner = new StringJoiner(",");
				for (Integer slot : slots) {
					joiner.add(slot + "");
				}
				print(joiner.toString());
			}

		} catch (Exception e) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_GENERIC_ERROR, e);
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	@Override
	public void getSlotNoProvidedRegistratioNo(String registrationNo, int parkingLevel) throws ParkingLotException {
		readWriteLock.readLock().lock();
		try {
			int value = parkingManager.getSlotNoProvidedRegistrationNo(parkingLevel, registrationNo);
			System.out.println(value != 0 ? value : "Not Found");
		} catch (Exception e) {
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_GENERIC_ERROR, e);
		} finally {
			readWriteLock.readLock().unlock();
		}

	}

	private static void print(String message) {
		System.out.println(message);
	}

	@Override
	public void restore() {
		parkingManager.cleanup();

	}

	@Override
	public Optional<Integer> getAvailability(int parkingLevel) throws ParkingLotException{
		readWriteLock.readLock().lock();
		Optional<Integer> availability = Optional.empty();
		
		try {
			
			availability = Optional.of(parkingManager.getCurrentAvailability(parkingLevel));
			
		} catch (Exception e) {
			
			throw new ParkingLotException(ErrorConstants.PARKING_LOT_GENERIC_ERROR, e);
			
		} finally {
			readWriteLock.readLock().unlock();
		}
		
		return availability;
	}

}
