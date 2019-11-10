/**
 * 
 */
package com.parker.parkinglot.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parker.parkinglot.internal.ParkingPolicy;
import com.parker.parkinglot.model.Vehicle;

/**
 * @author vikramsingh
 *
 */
public class ParkingManager {

	private Map<Integer, LevelParkingManager> levelParkingMap;

	private static ParkingManager parkingManager = null;

	public static ParkingManager init(List<Integer> levels, List<Integer> capacities,
			Map<Integer, ParkingPolicy> levelParkingPolicies) {
		if (parkingManager == null) {
			synchronized (ParkingManager.class) {
				parkingManager = new ParkingManager(levels, capacities, levelParkingPolicies);
			}
		}
		return parkingManager;
	}

	private ParkingManager(List<Integer> levels, List<Integer> capacities,
			Map<Integer, ParkingPolicy> levelParkingPolicies) {
		if (levelParkingMap == null) {

			levelParkingMap = new HashMap<>();
		}

		for (int i = 0; i < levels.size(); i++) {
			levelParkingMap.put(levels.get(i), LevelParkingManager.init(levels.get(i), capacities.get(i),
					levelParkingPolicies.get(levels.get(i))));

		}
	}

	public int park(int level, Vehicle vehicle) {
		return levelParkingMap.get(level).park(vehicle);
	}

	public boolean leave(int level, int slotNumber) {
		return levelParkingMap.get(level).leave(slotNumber);
	}

	public List<String> getStatus(int level) {
		return levelParkingMap.get(level).getStatus();
	}

	public int getCurrentAvailability(int level) {
		return levelParkingMap.get(level).getCurrentAvailability();
	}

	public List<String> getRegistrationNoProvidedColour(int level, String colour) {
		return levelParkingMap.get(level).getRegistrationNoProvidedColour(colour);
	}

	public List<Integer> getSlotNoProvidedColour(int level, String colour) {
		return levelParkingMap.get(level).getSlotNumbersProvidedColour(colour);
	}

	public int getSlotNoProvidedRegistrationNo(int level, String registrationNo) {
		return levelParkingMap.get(level).getSlotNoProvidedRegistrationNo(registrationNo);
	}

	public void cleanup() {
		for (LevelParkingManager levelParkingManager : levelParkingMap.values()) {
			levelParkingManager.cleanup();
		}
		levelParkingMap = null;
		parkingManager = null;
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
