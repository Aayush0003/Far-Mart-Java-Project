package com.yrl;

public class Voice extends Item{

	public Voice(String itemCode, String itemName, Double basePrice) {
		super(itemCode, itemName, basePrice);
		
		// TODO Auto-generated constructor stub
	}
	
	public Voice(String itemCode, String itemName, Double basePrice , String phone, int noOfDaysPurchased ) {
		//super(itemCode, Item.map.get(itemCode).getItemName(), Item.map.get(itemCode).getBasePrice());
		super(itemCode, itemName, basePrice);
		this.phone=phone;
		this.noOfDaysPurchased=noOfDaysPurchased;
	}
	
	

	private String phone;
	private int noOfDaysPurchased;
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getNoOfDaysPurchased() {
		return noOfDaysPurchased;
	}

	public void setNoOfDaysPurchased(int noOfDaysPurchased) {
		this.noOfDaysPurchased = noOfDaysPurchased;
	}

	@Override
	public double getTaxes() {
		//taxes/30*tax rat/
		
		return this.getSubTotal()*0.065;
		
	}

	@Override
	public double getSubTotal() {
		
		return (50 * this.getNoOfDaysPurchased())/30;
	}

	
	

	@Override
	public double getGrandTotal() {
		
		return this.getTaxes()+this.getSubTotal();
	}

	@Override
	public String toString() {
		return String.format("%s (%s) -Voice %s %s days @ 50.00/30 days ", getItemName() +getItemCode()+phone+noOfDaysPurchased);
	}
	
	
	
	
	
	
	
	

}
