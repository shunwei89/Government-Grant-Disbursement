package com.assignment.GovernmentGrantDisbursement.model;

public class Response {
	private String message;
	private Household household;
	
	public Response(Household household) {
		this.household = household;
	}
	
	public Response(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Household getHousehold() {
		return household;
	}
	public void setHousehold(Household household) {
		this.household = household;
	}
}
