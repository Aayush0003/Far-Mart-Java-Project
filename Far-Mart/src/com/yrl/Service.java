package com.yrl;

public class Service extends Item {

	private double TAX_RATE = 0.035;
	private Person person;

	private Double noOfHours;

	public Service(String itemCode, String itemName, Double basePrice) {
		super(itemCode, itemName, basePrice);
		// TODO Auto-generated constructor stub
	}

	public Service(String itemCode, String itemName, double basePrice, double noOfHours, Person UUID) {
		super(itemCode, itemName, basePrice);
		// super(itemCode, Item.map.get(itemCode).getItemName(),
		// Item.map.get(itemCode).getBasePrice());
		this.person = UUID;
		this.noOfHours = noOfHours;

	}

	public Double getNoOfHours() {
		return noOfHours;
	}

	public void setNoOfHours(Double noOfHours) {
		this.noOfHours = noOfHours;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

//	public double getHourlyRate() {
//		return hourlyRate;
//	}

//	public void setHourlyRate(double hourlyRate) {
//		this.hourlyRate = hourlyRate;
//	}

	@Override
	public double getSubTotal() {

		return (this.getBasePrice() * noOfHours) ;

	}

	@Override
	public double getTaxes() {

		return (TAX_RATE * this.getSubTotal());
	}

	public double getGrandTotal() {
		return getTaxes() + getSubTotal();
	}

	public String toString() {
		return String.format("%s (%s) -Served by %s %.2f hours @ %d /hour \n ",
				getItemName() + getItemCode() + getPerson().getName() + noOfHours + this.getBasePrice());
	}

	// @Override
//	public String toString() {
//		return "Service [person=" + person + ", hourlyRate=" + hourlyRate + ", noOfHours=" + noOfHours
//				+ ", getItemCode()=" + getItemCode() + ", getItemName()=" + getItemName() + "]";
//	}

//
//	@Override
//	public String toString() {
////		return "Service  person=" + person + ", hourlyRate=" + hourlyRate + ", noOfHours="
////				+ noOfHours + "]";
//		

}
