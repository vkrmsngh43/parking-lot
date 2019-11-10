package com.parker.parkinglot.model;

public class Vehicle {
	
	public String registrationNo;
	
	public String colour;
	
	
	public Vehicle(String registrationNo, String colour) {
		this.registrationNo = registrationNo;
		this.colour = colour;
	}
	
	
	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}


	@Override
	public String toString() {
		return "Vehicle [registrationNo=" + registrationNo + ", colour=" + colour + "]";
	}
	

}
