package com.yrl;

public class Data extends Item {

	double TAX_RATE = 0.055;

	public Data(String itemCode, String itemName, double basePrice) {
		super(itemCode, itemName, basePrice);
	}

	public Data(String itemCode, String itemName, double basePrice, double noOfGBPurchased) {
		super(itemCode, itemName, basePrice);
		this.noOfGBPurchased = noOfGBPurchased;
		this.baseGBPrice = basePrice;
	}

	private double noOfGBPurchased;
	private double baseGBPrice;

	public double getNoOfGBPurchased() {
		return noOfGBPurchased;
	}

	public double getbaseGBPrice() {
		return baseGBPrice;
	}

	
	@Override
	public double getTaxes() {
		

		return TAX_RATE*this.getSubTotal();
	}

	@Override
	public double getSubTotal() {
		double priceGB = noOfGBPurchased * baseGBPrice;

		return priceGB;
	}

	public double getGrandTotal() {
		return getSubTotal() + getTaxes();
	}

	public String toString() {
		return String.format("%s (%s) -Data %f @%f/GB", getItemName() + getItemCode() + noOfGBPurchased + baseGBPrice);
	}

}
