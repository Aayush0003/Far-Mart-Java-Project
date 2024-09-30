package com.yrl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Sale {

	private String saleCode;
	private Store store;
	private Person customer;
	private Person salesPerson;
	private LocalDate saleDate;
	private List<Item> purchased;

	public Sale(String saleCode, Store storeCode, Person customerCode, Person salesPersonCode, LocalDate saleDate) {

		this.saleCode = saleCode;
		this.store = storeCode;
		this.customer = customerCode;
		this.salesPerson = salesPersonCode;
		this.saleDate = saleDate;
		this.purchased = new ArrayList<>();
	}

	public int getNumItems() {
		return this.purchased.size();
	}

	public String getSaleCode() {
		return saleCode;
	}

	public Store getStore() {
		return store;
	}

	public Person getCustomer() {
		return customer;
	}

	public Person getSalesPerson() {
		return salesPerson;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void getAddItem(Item i) {
		this.purchased.add(i);
	}

	public List<Item> getPurchased() {
		return purchased;
	}

	public double getTotalTax() {
		double totalTax = 0.0;
		for (Item i : this.purchased) {

			totalTax += i.getTaxes();

		}
		return totalTax;
	}

	public double getsubTotal() {
		double subTotal = 0.0;
		
		for (Item i : this.purchased) {
			subTotal += i.getSubTotal();

		}
		return subTotal;
	}

	public double getGrandTotal() {
		
		return this.getsubTotal()+this.getTotalTax();
	}

//	public static final Comparator<Sale> ComparebyGrandtotal = new Comparator<>() {
//
//		public int compare(Sale s1, Sale s2) {
//			return Double.compare(s2.getGrandTotal(), s1.getGrandTotal());
//		}
//	};
	
	public static final Comparator<Sale> ComparebyName = new Comparator<>() {

		public int compare(Sale s1, Sale s2) {
			return (s2.getCustomer().getName().compareTo(s1.getCustomer().getName()));
		}
	};
	public static final Comparator<Sale> ComparebyGrandTotal = new Comparator<>() {

		public int compare(Sale s1, Sale s2) {
			return Double.compare(s2.getGrandTotal(), s1.getGrandTotal());
		}
	};
	
	public static final Comparator<Sale> ComparebyStore = new Comparator<>() {

		public int compare(Sale s1, Sale s2) {
			int x = s1.getStore().getStoreCode().compareTo(s2.getStore().getStoreCode());
			if(x==0) {
				x = s2.getSalesPerson().getName().compareTo(s1.getSalesPerson().getName()); 
			} 
			return x;
		}
	};

	static Comparator<Sale> byNameTotal = ComparebyName.thenComparing(ComparebyGrandTotal);


}


