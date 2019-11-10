package com.parker.parkinglot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.parker.parkinglot.constants.DisplayConstants;
import com.parker.parkinglot.exception.ParkingLotException;
import com.parker.parkinglot.processor.ExecutorService;
import com.parker.parkinglot.service.impl.ParkingLotServiceImpl;

public class ParkingLotApplication {

	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				executeInteractiveMode();
			} else {
				executeNonIteractiveMode(args[0]);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void executeInteractiveMode() {

		System.out.print(DisplayConstants.displayMessage);
		ExecutorService executorService = new ExecutorService();
		executorService.init(new ParkingLotServiceImpl());
		BufferedReader reader = null;
		String input = null;
		try {
			while (true) {

				reader = new BufferedReader(new InputStreamReader(System.in));
				input = reader.readLine().trim();
				if (input.equalsIgnoreCase("exit")) {
					
					System.out.println("Thank you!");
					break;
					
				} else {
					executorService.execute(input);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void executeNonIteractiveMode(String fileName) throws ParkingLotException {
		String input = null;
		File inputFile = new File(fileName);
		BufferedReader bufferReader = null;
		try {
			bufferReader = new BufferedReader(new FileReader(inputFile));
			ExecutorService executorService = new ExecutorService();
			executorService.init(new ParkingLotServiceImpl());

			while ((input = bufferReader.readLine()) != null) {
				input = input.trim();
				try {
					executorService.execute(input);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}
		} catch (Exception e) {
			throw new ParkingLotException("File Not Found", e);
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
			} catch (IOException e) {
				throw new ParkingLotException("An IO exception occurred.", e);
			}
		}
	}

}
