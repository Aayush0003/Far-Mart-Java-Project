package com.yrl;

public class Purchased extends Product {

	private double TAX_RATE = 0.065;
	
	public Purchased(String itemCode, String itemName, Double basePrice) {
		super(itemCode, itemName, basePrice);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
   public double getTaxes() {
	   
	   return (( TAX_RATE * (this.getBasePrice())));
        
   }

	@Override
	public double getSubTotal() {
		
		
		return this.getBasePrice();
	}

	
	
	
}
