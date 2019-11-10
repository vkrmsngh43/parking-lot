package com.parker.parkinglot.processor;

import com.parker.parkinglot.config.Commands;
import com.parker.parkinglot.exception.ParkingLotException;
import com.parker.parkinglot.model.Car;
import com.parker.parkinglot.service.ParkingLotService;

public class ExecutorService {

	private ParkingLotService parkingLotService;

	public void init(ParkingLotService parkingLotService) {
		this.parkingLotService = parkingLotService;
	}

	public void execute(String input) throws ParkingLotException {

		String[] inputFragments = input.split(" ");
		String command = inputFragments[0];
		if (!isValid(command, inputFragments.length - 1)) {
			System.out.println("Invalid Command.");
		}
		switch (command) {
		case Commands.CREATE_PARKING_LOT:
			int capacity = Integer.parseInt(inputFragments[1]);
			parkingLotService.createParkingSlots(capacity, 1);
			break;
		case Commands.LEAVE:
			int slotNumber = Integer.parseInt(inputFragments[1]);
			parkingLotService.leave(slotNumber, 1);
			break;
		case Commands.PARK:
			Car car = new Car(inputFragments[1], inputFragments[2]);
			parkingLotService.park(car, 1);
			break;
		case Commands.REGISTRATION_NO_FOR_CARS_WITH_COLOUR:
			parkingLotService.getRegistrationNoProvidedColour(inputFragments[1], 1);
			break;
		case Commands.SLOT_NO_FOR_CARS_WITH_COLOUR:
			parkingLotService.getSlotNoProvidedColour(inputFragments[1], 1);
			break;
		case Commands.SLOT_NO_FOR_REGISTRATION_NO:
			parkingLotService.getSlotNoProvidedRegistratioNo(inputFragments[1], 1);
			break;
		case Commands.STATUS:
			parkingLotService.getStatus(1);
			break;

		default:
			break;
		}

	}

	private boolean isValid(String command, int argCounts) {
		return Commands.getCommndVsArgCountMapping().get(command) == argCounts;
	}
}
