package com.yrl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeasedProduct extends Product {

	public LeasedProduct(String itemCode, String itemName, Double basePrice) {
		super(itemCode, itemName, basePrice);

	}

	public LeasedProduct(String saleCode, String itemCode, Double basePrice, LocalDate startDate, LocalDate endDate) {
		super(saleCode, itemCode, basePrice);
		this.startDate = startDate;
		this.endDate = endDate;

	}

	private LocalDate startDate;
	private LocalDate endDate;

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getTaxes() {
		return 0;
	}

	public double getMarkup() {
		return this.getBasePrice() * 0.50;
	}

	public double getMonths() {
		long months = ChronoUnit.MONTHS.between(startDate,endDate);
		return months;
	}

	public double getSubTotal() {
		long months = ChronoUnit.MONTHS.between(startDate, endDate);
		double subTotal = ((this.getBasePrice()+this.getMarkup())) / months;
		return subTotal;

	}

	@Override
	public double getGrandTotal() {

		return getTaxes() + getSubTotal();
	}

	public String toString() {
		return String.format("%s (%s)  Lease for %d months", getItemName() + getItemCode() + getMonths());
	}

}
