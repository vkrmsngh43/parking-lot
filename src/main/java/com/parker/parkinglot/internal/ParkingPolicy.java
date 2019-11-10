/**
 * 
 */
package com.parker.parkinglot.internal;

/**
 * @author vikramsingh
 *
 */
public interface ParkingPolicy {
	
	/**
	 * Gets the next available slot based on the parking policy
	 * @return
	 */
	public int getSlot();
	
	/**
	 * Adds the freed slot back to the available pool of slots
	 * @param slot
	 * @return
	 */
	
	public void addSlot(int slot);
	
	/**
	 * Removes the allocated slot from the pool of available slots
	 * @param slot
	 */
	
	public boolean removeSlot(int slot);

}
