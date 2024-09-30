/*
 *
 *Author: Aayush Subedi
 *  This class calls the data required for the Store file. 
 *  It also has the constructors and getters.
 */

package com.yrl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Store {

	private String storeCode;
	private Person managerUUID;
	private Address address;
	private List<Sale> purchased = new ArrayList<>();

	public Store(String storeCode, Person managerUUID, Address address) {

		this.storeCode = storeCode;
		this.managerUUID = managerUUID;
		this.address = address;
		
	}

	public Address getAddress() {
		return address;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public List<Sale> getPurchased() {
		return purchased;
	}

	public void addPurchased(Sale pur) {
		this.purchased.add(pur);
	}

	public Person getManagerUuid() {
		return managerUUID;
	}

	public void getAddSale(Sale i) {
		this.purchased.add(i);
	}

	public void printStore() {
		System.out.println(getStoreCode() + getManagerUuid());
	}

	public double getSubTotal() {
		double subTotal = 0.0;
		for (Sale sale : this.purchased) {
			subTotal += sale.getsubTotal();
		}
		return subTotal;
	}

	public double getTaxes() {
		double tax = 0.0;
		for (Sale sale : this.purchased) {
			tax += sale.getTotalTax();
		}
		return tax;
	}

	public double getGrandTotal() {
		
		return this.getSubTotal()+this.getTaxes();
	}

	public static final Comparator<Store> ComparebyName = new Comparator<>() {

		public int compare(Store s1, Store s2) {
			return (s1.getManagerUuid().getName().compareTo(s2.getManagerUuid().getName()));
		}
	};
	public static final Comparator<Store> ComparebyGrandTotal = new Comparator<>() {

		public int compare(Store s1, Store s2) {
			return Double.compare(s2.getGrandTotal(), s1.getGrandTotal());
		}
	};

	static Comparator<Store> byNameTotal = ComparebyName.thenComparing(ComparebyGrandTotal);
}
