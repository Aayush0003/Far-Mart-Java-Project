package com.yrl;

public abstract class Item  {
	private String itemCode;
	private String itemName;
	private Double basePrice;
	
	// public static HashMap<String, Item> map = new HashMap<>();

	public String getItemCode() {
		return itemCode;
	}

	
	public String getItemName() {
		return itemName;
	}


	public Double getBasePrice() {
		return basePrice;
	}

	
	public Item(String itemCode, String itemName, Double basePrice) {
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.basePrice = basePrice;
	}

	
	public abstract double getTaxes();
	public abstract double getSubTotal();
	public double getGrandTotal() {
		return this.getTaxes()+this.getSubTotal();
	}
	
	
		
	}

