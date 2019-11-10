/**
 * 
 */
package com.parker.parkinglot.service;

import java.util.Optional;

import com.parker.parkinglot.exception.ParkingLotException;
import com.parker.parkinglot.model.Vehicle;

/**
 * @author vikramsingh
 *
 */
public interface ParkingLotService {
	
	/**
	 * Creates parking slot with the given capacity and for the given level
	 * @param capacity
	 * @param parkingLevel
	 */
	public void createParkingSlots(int capacity, int parkingLevel) throws ParkingLotException;
	
	/**
	 * Parks the given vehicle as per the ParkingPolicy
	 * @param vehicle
	 * @param parkingLevel
	 * @return
	 */
	public Optional<Integer> park(Vehicle vehicle, int parkingLevel) throws ParkingLotException;
	
	/**
	 * Removes the car from the parking lot parked at the given slotNumber
	 * @param slotNumber
	 * @param parkingLevel
	 */
	public void leave(int slotNumber, int parkingLevel) throws ParkingLotException;
	
	/**
	 * Gives the status (occupied slots )of the parkingLot at a given time.
	 * @param parkingLevel
	 */
	public void getStatus(int parkingLevel) throws ParkingLotException;
	
	/**
	 * Gets all the cars' registration numbers of a given colour on a given parking level
	 * @param color
	 * @param parkingLevel
	 */
	public void getRegistrationNoProvidedColour(String color, int parkingLevel) throws ParkingLotException;
	
	/**
	 * Gets all the slot numbers at which given colour's car are parked
	 * @param colour
	 * @param parkingLevel
	 */
	public void getSlotNoProvidedColour(String colour, int parkingLevel) throws ParkingLotException;
	
	/**
	 * Gets slot number of a car with given registration number 
	 * @param registrationNo
	 * @param parkingLevel
	 */
	public void getSlotNoProvidedRegistratioNo(String registrationNo, int parkingLevel) throws ParkingLotException;
	
	/**
	 * To restore the application to initial state.
	 */
	public void restore();
	
	
	public Optional<Integer> getAvailability(int parkingLevel) throws ParkingLotException;
}
