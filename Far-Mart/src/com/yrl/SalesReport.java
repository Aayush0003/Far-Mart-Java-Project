package com.yrl;

import java.util.Collections;
import java.util.List;

public class SalesReport {
	
	
    public static void customerSaleReport(LinkedLists<Sale> sales) {
    	System.out.println("Sale       Store      Customer                 Salesperson               Total \n");
    	for(Sale s: sales) {
    		System.out.printf("%-10s %-10s %-20s %-29s %-15f\n",s.getSaleCode(),s.getStore().getStoreCode(),s.getCustomer().getNameinorder(),s.getSalesPerson().getNameinorder(),s.getGrandTotal());
    		System.out.println();
    	}
    	
    }

	public static void main(String args[]) {

//		String personFile = "data/Persons.csv";
//		String storeFile = "data/Stores.csv";
//		String itemFile = "data/Items.csv";
//		String saleFile = "data/Sales.csv";
//		String saleItem = "data/SaleItems.csv";

		List<Person> people = DatabaseLoader.loadPerson();
		List<Store> storeResult = DatabaseLoader.loadStore(people);
		List<Item> itemResult = DatabaseLoader.loadItem();
		List<Sale> saleResult = DatabaseLoader.loadSale(storeResult, people);
		DatabaseLoader.loadSaleItem(itemResult, saleResult, people);
		LinkedLists<Sale> customerSale = new LinkedLists <Sale>(Sale.ComparebyName);
		LinkedLists<Sale> totalSale = new LinkedLists <Sale>(Sale.ComparebyGrandTotal);
		LinkedLists<Sale> salesPersonSale = new LinkedLists <Sale>(Sale.ComparebyStore);
		for(Sale s : saleResult ) {
			customerSale.addElement(s);
			totalSale.addElement(s);
			salesPersonSale.addElement(s);
		}
		System.out.println("+-------------------------------------------------------------------------+\n");
		System.out.println("| Sales by Customer                                                       |\n");
		System.out.println("+-------------------------------------------------------------------------+\n");
		customerSaleReport(customerSale);
		System.out.println("+-------------------------------------------------------------------------+\n");
		System.out.println("| Sales by Total                                                          |\n");
		System.out.println("+-------------------------------------------------------------------------+\n");
		customerSaleReport(totalSale);
		System.out.println("+-------------------------------------------------------------------------+\n");
		System.out.println("| Sales by Store                                                          |\n");
		System.out.println("+-------------------------------------------------------------------------+\n");
		customerSaleReport(salesPersonSale);
		

//		List<Person> people = DataLoader.loadPerson(personFile);
//		List<Store> storeResult = DataLoader.loadStore(storeFile,people);
//		List<Item> itemResult = DataLoader.loadItem(itemFile);
//		List<Sale> saleResult = DataLoader.loadSale(saleFile,storeResult, people);
//		DataLoader.loadSaleItem(saleItem,itemResult, saleResult, people,storeResult);

//		summaryReport(saleResult, storeResult);
//		storeSalesSummary(saleResult, storeResult);
//		individualSummary(saleResult, itemResult);
		
		

	}

	public static void summaryReport(List<Sale> saleList, List<Store> storeList) {
		Collections.sort(saleList, Sale.ComparebyGrandTotal);
		int totalItems = 0;
		double totalTax = 0.0;
		double totalGrandTotal = 0.0;

		System.out
				.println("+----------------------------------------------------------------------------------------+");
		System.out.println("| Summary Report - By Total");
		System.out
				.println("+----------------------------------------------------------------------------------------+");
		System.out.printf("%-10s%-10s%-20s%-30s%-10s%-10s", "Invoice#", "Store", "Customer", "Num Items", "Tax",
				"Total\n");

		for (Sale s : saleList) {

			totalTax += s.getTotalTax();
			totalGrandTotal += s.getsubTotal();
			totalItems += s.getPurchased().size();

			System.out.printf("%-10s %-10s %-20s %-30d$ %.2f$ %.2f \n", s.getSaleCode(), s.getStore().getStoreCode(),
					s.getCustomer().getName(), s.getPurchased().size(), s.getTotalTax(), s.getsubTotal());
		}

		System.out
				.println("+----------------------------------------------------------------------------------------+");
		System.out.printf("%48d      $ %8.2f   %11.2f \n\n\n", totalItems, totalTax, totalGrandTotal);
	}

	public static void storeSalesSummary(List<Sale> saleList, List<Store> storeList) {

		Collections.sort(storeList, Store.byNameTotal);
		int numOfSales = 0;
		double totalGrandTotal = 0;
		int i = 0;

		System.out.println("+----------------------------------------------------------------+");
		System.out.println("| Store Sales Summary Report                                     |");
		System.out.println("+----------------------------------------------------------------+");

		System.out.printf("%-10s%-20s%-20s%-20s \n", "Store", "Manager", "Sales", "GrandTotal");

		for (Store s : storeList) {

			
			numOfSales += s.getPurchased().size();
		
			//System.out.println("sales"+numOfSales);
			totalGrandTotal += s.getGrandTotal();

			System.out.printf("%-10s%-10s%10d      %20.2f\n", s.getStoreCode(), s.getManagerUuid().getName(),
					s.getPurchased().size(), s.getGrandTotal());

		}
		System.out.println("+----------------------------------------------------------------+");
		System.out.printf("%33d %22.2f\n\n\n", numOfSales, totalGrandTotal);

	}

	public static void individualSummary(List<Sale> saleList, List<Item> itemList) {

		for (Sale s : saleList) {

			System.out.println("Sale     #" + s.getSaleCode());
			System.out.println("Store    #" + s.getStore().getStoreCode());
			System.out.println("Date     #" + s.getSaleDate());

			System.out.printf("Customer:\n%s (%s)\n\t[", s.getCustomer().getName(), s.getCustomer().getUUID(),s.getCustomer().getEmailList());
			

			


			System.out.println("Sales Person: " + s.getSalesPerson().getName() + "(" + s.getSalesPerson().getUUID()
					+ ")" + "[" + s.getSalesPerson().getEmailList() + "]" + s.getSalesPerson().getAddress());

			System.out.printf("Items(%-1s),             Tax      Total\n ", s.getNumItems());
			System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                          -=-=-=-=-=- -=-=-=-=-=-");
			System.out.println("                                                             -=-=-=-=-=- -=-=-=-=-=-");

			double Totaltaxes = 0.0;
			double Total = 0.0;
			double grandTotal = 0.0;

			for (Item i : s.getPurchased()) {

				Totaltaxes += i.getTaxes();
				Total += i.getSubTotal();
				grandTotal = Totaltaxes + Total;
				if (i instanceof Purchased) {
					System.out.printf("%s (%s)\n", i.getItemName(), i.getItemCode());
					System.out.printf("%-70s$%.2f$%10.2f\n", " ", i.getTaxes(), i.getSubTotal());
				} else if (i instanceof LeasedProduct) {
					System.out.printf("%s (%s) - Lease for %.0f months\n", i.getItemName(), i.getItemName(),
							((LeasedProduct) i).getMonths());
					System.out.printf("%-70s$%10.2f$%10.2f\n", " ", i.getTaxes(), i.getSubTotal());
				} else if (i instanceof Service) {
					System.out.printf("%s (%s) - Served by %s\n", i.getItemName(), i.getItemCode(),
							((Service) i).getPerson().getName());
					System.out.printf("%s hours @ %s/hour\n", ((Service) i).getNoOfHours(), i.getBasePrice());
					System.out.printf("%-70s$%10.2f$%10.2f\n", " ", ((Service) i).getTaxes(), ((Service) i).getSubTotal());
				} else if (i instanceof Data) {
					System.out.printf("%s (%s) - Data\n", i.getItemName(), i.getItemCode());
					System.out.printf("%s GB @ %s/GB\n", ((Data) i).getNoOfGBPurchased(), i.getBasePrice());
					System.out.printf("%-70s$%10.2f$%10.2f\n", " ", ((Data) i).getTaxes(), ((Data) i).getSubTotal());
				} else if (i instanceof Voice) {
					System.out.printf("%s (%s) - Voice %s\n", i.getItemName(), i.getItemCode(), ((Voice) i).getPhone());
					System.out.printf("%s days @ $%s/ 30 days \n", ((Voice) i).getNoOfDaysPurchased(),
							i.getBasePrice());
					System.out.printf("%-70s$%10.2f$%10.2f\n", " ", ((Voice) i).getTaxes(), ((Voice) i).getSubTotal());
				}

			}

			
			System.out.println("                                                             -=-=-=-=-=- -=-=-=-=-=-");
			System.out.printf("                                                  SubTotal$%20.2f%20.2f\n", s.getTotalTax(),
					s.getsubTotal());
			System.out.printf("                                                GrandTotal$%20.2f\n", s.getGrandTotal());

		}
	}
}
