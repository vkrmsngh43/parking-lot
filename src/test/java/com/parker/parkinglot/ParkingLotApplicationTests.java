package com.parker.parkinglot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.parker.parkinglot.model.Car;
import com.parker.parkinglot.service.ParkingLotService;
import com.parker.parkinglot.service.impl.ParkingLotServiceImpl;

public class ParkingLotApplicationTests {
	
	//to hold all the sysOuts for the commands
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	private ParkingLotService parkingLotService = null;
	
	
	@Before
	public void initialise() {
		parkingLotService = new ParkingLotServiceImpl();
		System.setOut(new PrintStream(output));
	}

	@After
	public void cleanUp() {
		System.setOut(null);
		parkingLotService.restore();
	}

	@Test
	public void createParkingSlots() throws Exception {

		parkingLotService.createParkingSlots(6, 1);
		assertTrue("Created a parking lot with 6 slots".equalsIgnoreCase(output.toString().trim()));

	}
	
	@Test
	public void testParkingAvailability() throws Exception
	{
		parkingLotService.createParkingSlots(6, 1);
		parkingLotService.park(new Car("KA-01-HH-1234", "White"), 1);
		parkingLotService.park(new Car("KA-01-HH-9999", "White"), 1);
		parkingLotService.park(new Car("KA-01-BB-0001", "Black"), 1);
		int parkingSlotsLeft = parkingLotService.getAvailability(1).get();
		assertEquals(3, parkingSlotsLeft);
	}
	
	@Test
	public void testNearestFirstParkingPolicy() throws Exception{
		parkingLotService.createParkingSlots(3, 1);
		parkingLotService.park(new Car("KA-01-HH-1234", "White"), 1);
		
		assertTrue("Created a parking lot with 3 slots\nAllocated slot number: 1"
				.equalsIgnoreCase(output.toString().trim()));
	}
	
	@Test
	public void testPArkingLotOverflow() throws Exception{
		parkingLotService.createParkingSlots(2, 1);
		parkingLotService.park(new Car("KA-01-HH-1234", "White"), 1);
		parkingLotService.park(new Car("KA-01-HH-9999", "White"), 1);
		parkingLotService.park(new Car("KA-01-BB-0001", "Black"), 1);
		
		assertTrue("Created a parking lot with 2 slots\nAllocated slot number: 1\nAllocated slot number: 2\nSorry, parking lot is full".equalsIgnoreCase(output.toString().trim()));
		
	}
	
	@Test
	public void testVehicleLeaving() throws Exception{
		parkingLotService.createParkingSlots(2, 1);
		parkingLotService.park(new Car("KA-01-HH-1234", "White"), 1);
		parkingLotService.leave(1, 1);
		
		assertTrue("Created a parking lot with 2 slots\nAllocated slot number: 1\nSlot number 1 is free".equalsIgnoreCase(output.toString().trim()));
	}
	
	/**
	 * 
	 * @throws Exception
	 
	@Test
	public void testParkingLotStatus() throws Exception{
		parkingLotService.createParkingSlots(5, 1);
		parkingLotService.park(new Car("KA-01-HH-1234", "White"), 1);
		
		parkingLotService.getStatus(1);
		
		String expectedMessagesString = "Created a parking lot with 5 slots\nAllocated slot number: 1\nSlot No.\tRegistration No\tColour\n1\tKA-01-HH-1234\tWhite";
		
		assertTrue(expectedMessagesString.equalsIgnoreCase(output.toString().trim()));
	}
	*/
}
