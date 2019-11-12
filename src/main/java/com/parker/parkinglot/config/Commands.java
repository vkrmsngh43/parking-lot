/**
 * 
 */
package com.parker.parkinglot.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author vikramsingh
 *
 */
public class Commands {
	
	public static final String CREATE_PARKING_LOT = "create_parking_lot";
	public static final String PARK = "park";
	public static final String STATUS = "status";
	public static final String LEAVE = "leave";
	public static final String REGISTRATION_NO_FOR_CARS_WITH_COLOUR = "registration_numbers_for_cars_with_colour";
	public static final String  SLOT_NO_FOR_CARS_WITH_COLOUR = "slot_numbers_for_cars_with_colour";
	public static final String SLOT_NO_FOR_REGISTRATION_NO = "slot_number_for_registration_number";
	
	private static Map<String, Integer> commandVsArgCount = new HashMap<String, Integer>();
	
	static {
		commandVsArgCount.put(CREATE_PARKING_LOT, 1);
		commandVsArgCount.put(PARK, 2);
		commandVsArgCount.put(STATUS, 0);
		commandVsArgCount.put(LEAVE, 1);
		commandVsArgCount.put(REGISTRATION_NO_FOR_CARS_WITH_COLOUR, 1);
		commandVsArgCount.put(SLOT_NO_FOR_CARS_WITH_COLOUR, 1);
		commandVsArgCount.put(SLOT_NO_FOR_REGISTRATION_NO, 1);
	}
	
	public static Map<String, Integer> getCommndVsArgCountMapping() {
		return commandVsArgCount;
	}
	
	public static Set<String> getAllCommands() {
		return commandVsArgCount.keySet();
	}

}
