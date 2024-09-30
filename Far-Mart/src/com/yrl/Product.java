package com.yrl;

public class Product extends Item {

	private String itemCode;
	private String itemName;
	private Double basePrice;
	
	public Product(String itemCode, String itemName, Double basePrice) {
		super(itemCode, itemName, basePrice);
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.basePrice = basePrice;
		
	
	}

//	public Product(String saleCode, String itemCode) {
//		super(itemCode, Item.map.get(itemCode).getItemName(), Item.map.get(itemCode).getBasePrice());
//		
//	}

	public String getItemCode() {
		return itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public Double getBasePrice() {
		return basePrice;
	}


	@Override
	public double getTaxes() {
		return 0;
	}	@Override
	public double getSubTotal() {
		return 0;
	}	
	

	@Override
	public double getGrandTotal() {
		// TODO Auto-generated method stub
		return 0;
	} 
	@Override
	public String toString() {
		return String.format("%s (%s)", getItemName(),getItemCode());
	}
	
 
	//protected abstract double getTaxes();
	//protected abstract double getPurchased();
	
	
	
}
