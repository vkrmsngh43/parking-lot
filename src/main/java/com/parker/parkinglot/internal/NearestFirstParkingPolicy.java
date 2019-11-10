/**
 * 
 */
package com.parker.parkinglot.internal;

import java.util.TreeSet;

/**
 * @author vikramsingh
 *
 */
public class NearestFirstParkingPolicy implements ParkingPolicy{
	
	private TreeSet<Integer> slotsPool;
	
	public NearestFirstParkingPolicy() {
		slotsPool = new TreeSet<Integer>();
	}
	
	@Override
	public int getSlot() {
		
		return slotsPool.first();
	}

	@Override
	public void addSlot(int slot) {
		
		slotsPool.add(slot);
	}

	@Override
	public boolean removeSlot(int slot) {
		
		return slotsPool.remove(slot);
	}

}
