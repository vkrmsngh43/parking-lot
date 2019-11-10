package com.parker.parkinglot.exception;

public class ParkingLotException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param message
	 * @param throwable
	 */
	public ParkingLotException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
	/**
	 * @param throwable
	 */
	public ParkingLotException(Throwable throwable)
	{
		super(throwable);
	}
	
	public ParkingLotException(String message) {
		super(message);
	}

}
