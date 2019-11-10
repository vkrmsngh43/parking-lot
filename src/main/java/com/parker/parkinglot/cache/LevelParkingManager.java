/**
 * 
 */
package com.parker.parkinglot.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.parker.parkinglot.internal.NearestFirstParkingPolicy;
import com.parker.parkinglot.internal.ParkingPolicy;
import com.parker.parkinglot.model.Vehicle;

/**
 * @author vikramsingh
 *
 */
public class LevelParkingManager {


	private Map<Integer, Optional<Vehicle>> vehicleParkingMap;

	private static LevelParkingManager levelParkingManager = null;
	
	private AtomicInteger level = new AtomicInteger(0);
	private AtomicInteger capacity = new AtomicInteger();
	private AtomicInteger currentAvailability = new AtomicInteger();
	
	private ParkingPolicy parkingPolicy;
	
	
	public static LevelParkingManager init(int level, int capacity, ParkingPolicy parkingPolicy) {
		
		if (levelParkingManager == null) {
			synchronized (LevelParkingManager.class) {
				if (levelParkingManager == null) {
					levelParkingManager = new LevelParkingManager(level, capacity, parkingPolicy);
				}
			}
		}
		return levelParkingManager;
	}

	private LevelParkingManager(int level, int capacity, ParkingPolicy parkingPolicy) {
		
		this.vehicleParkingMap = new ConcurrentHashMap<>();
		this.level.set(level);
		this.capacity.set(capacity);
		this.currentAvailability.set(capacity);
		this.parkingPolicy = parkingPolicy;
		//default policy in case not specified
		if (this.parkingPolicy == null) {
			this.parkingPolicy = new NearestFirstParkingPolicy();
		}

		for (int i = 1; i <= capacity; i++) {
			vehicleParkingMap.put(i, Optional.empty());
			this.parkingPolicy.addSlot(i);
		}
	}

	public int park(Vehicle vehicle) {
		
		if (currentAvailability.get() == 0) {
			return -1;
		}

		int availableSlot = parkingPolicy.getSlot();
		if (vehicleParkingMap.containsValue(Optional.of(vehicle))) {
			return -1;
		}

		this.vehicleParkingMap.put(availableSlot, Optional.of(vehicle));
		this.currentAvailability.decrementAndGet();
		this.parkingPolicy.removeSlot(availableSlot);
		return availableSlot;
	}

	public boolean leave(int slotNumber) {
		
		if (!vehicleParkingMap.get(slotNumber).isPresent()) {
			return false;
		}
			
		currentAvailability.incrementAndGet();
		parkingPolicy.addSlot(slotNumber);
		vehicleParkingMap.put(slotNumber, Optional.empty());
		return true;
	}



	public int getCurrentAvailability() {
		return currentAvailability.get();
	}

	public List<String> getRegistrationNoProvidedColour(String colour) {
		List<String> statusList = new ArrayList<>();
		for (int i = 1; i <= capacity.get(); i++) {
			Optional<Vehicle> vehicle = vehicleParkingMap.get(i);
			if (vehicle.isPresent() && colour.equalsIgnoreCase(vehicle.get().getColour())) {
				statusList.add(vehicle.get().getRegistrationNo());
			}
		}
		return statusList;
	}

	public List<Integer> getSlotNumbersProvidedColour(String colour) {
		List<Integer> slotList = new ArrayList<>();
		for (int i = 1; i <= capacity.get(); i++) {
			Optional<Vehicle> vehicle = vehicleParkingMap.get(i);
			if (vehicle.isPresent() && colour.equalsIgnoreCase(vehicle.get().getColour())) {
				slotList.add(i);
			}
		}
		return slotList;
	}

	public int getSlotNoProvidedRegistrationNo(String registrationNo) {
		int result = 0;
		for (int i = 1; i <= capacity.get(); i++) {
			Optional<Vehicle> vehicle = vehicleParkingMap.get(i);
			if (vehicle.isPresent() && registrationNo.equalsIgnoreCase(vehicle.get().getRegistrationNo())) {
				result = i;
			}
		}
		return result;
	}
	
	public List<String> getStatus() {
		
		List<String> statusList = new ArrayList<>();
		
		for (int i = 1; i <= capacity.get(); i++) {
			Optional<Vehicle> vehicle = vehicleParkingMap.get(i);
			if (vehicle.isPresent()) {
				statusList.add(i + "\t\t" + vehicle.get().getRegistrationNo() + "\t\t" + vehicle.get().getColour());
			}
		}
		return statusList;
	}
	
	public void cleanup() {
		levelParkingManager = null;
		
		this.level = new AtomicInteger();

		this.currentAvailability = new AtomicInteger();
		this.capacity = new AtomicInteger();
		
		this.parkingPolicy = null;
		
		vehicleParkingMap = null;
		
	}
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

}
