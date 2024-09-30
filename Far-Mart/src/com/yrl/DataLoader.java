package com.yrl;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {

	public static List<Person> loadPerson(String fileName) {

		List<Person> PersonA = new ArrayList<>();
		Scanner s = null;

		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		// tokenzing the data
		while (s.hasNext()) {
			String line = s.nextLine();
			String tokens[] = line.split(",");
			String Uuid = tokens[0];
			String firstName = tokens[1];
			String lastName = tokens[2];
			String street = tokens[3];
			String city = tokens[4];
			String state = tokens[5];
			String zip = tokens[6];
			Address address = new Address(street, city, state, zip);
			List<String> emailList = new ArrayList<>(); // making an arrayList that can store the number of emails
			Person a = new Person(Uuid, firstName, lastName, address, emailList);

			for (int i = 7; i < tokens.length; i++) {
				a.addEmail(tokens[i]);
			}

			PersonA.add(a);
		}
		s.close();
		return PersonA;
	}

	// loads the csv data for Store class
	public static List<Store> loadStore(String fileName, List<Person> people) {

		List<Store> storeDataA = new ArrayList<>();

		Scanner s = null;
		Person manager = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		// tokenzing the data
		s.nextLine();
		while (s.hasNext()) {
			String line = s.nextLine();
			String tokens[] = line.split(",");
			String storeCode = tokens[0];
			String managerCode = tokens[1];
			String street = tokens[2];
			String city = tokens[3];
			String state = tokens[4];
			String zip = tokens[5];

			for (Person p : people) {
				if (p.getUUID().equals(managerCode)) {
					manager = p;
				}
			}
			Address address = new Address(street, city, state, zip);

			Store a = new Store(storeCode, manager, address);

			storeDataA.add(a);
		}
		s.close();
		return storeDataA;
	}

	// loads the csv data for Items class
	public static List<Item> loadItem(String fileName) {

		List<Item> saleItemA = new ArrayList<>();

		Scanner s = null;

		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		// tokenzing the data
		s.nextLine();
		while (s.hasNext()) {

			String line = s.nextLine();
			String tokens[] = line.split(",");
			String itemCode = tokens[0];
			String itemType = tokens[1];
			String itemName = tokens[2];
			Double basePrice = Double.parseDouble(tokens[3]);

			if (itemType.equals("P")) {
				saleItemA.add(new Product(itemCode, itemName, basePrice));
			} else if (itemType.equals("S")) {

				saleItemA.add(new Service(itemCode, itemName, basePrice));

			} else if (itemType.equals("D")) {
				saleItemA.add(new Data(itemCode, itemName, basePrice));

			} else if (itemType.equals("V")) {
				saleItemA.add(new Voice(itemCode, itemName, basePrice));
			}
		}
		s.close();
//		for (Item i : saleItemA) {
//			Item.map.put(i.getItemCode(), i);
//		}
		return saleItemA;
	}

	// TODO: need some if statements here for the store and others
	public static List<Sale> loadSale(String fileName, List<Store> storeList, List<Person> personList) {
		List<Sale> saleDataA = new ArrayList<>();
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		s.nextLine();
		while (s.hasNext()) {
			String line = s.nextLine();
			String tokens[] = line.split(",");
			String saleCode = tokens[0];
			Store storeCode = null;
			for (Store n : storeList) {
				if (tokens[1].equals(n.getStoreCode())) {
					storeCode = new Store(n.getStoreCode(), n.getManagerUuid(), n.getAddress());
				}
			}
			Person customerCode = null;
			for (Person n : personList) {
				if (tokens[2].equals(n.getUUID())) {
					customerCode = new Person(n.getUUID(), n.getFirstName(), n.getLastName(), n.getAddress(),
							n.getEmailList());
				}
			}
			Person salesPersonCode = null;
			for (Person n : personList) {
				// if (tokens[3].equalsIgnoreCase(saleCode)) {
				if (tokens[3].equals(n.getUUID())) {
					salesPersonCode = new Person(n.getUUID(), n.getFirstName(), n.getLastName(), n.getAddress(),
							n.getEmailList());
				}
			}
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
			LocalDate saleDate = LocalDate.parse(tokens[4]);

			Sale n = new Sale(saleCode, storeCode, customerCode, salesPersonCode, saleDate);

			for (Store store : storeList) {
				if (tokens[1].equals(store.getStoreCode())) {
					store.addPurchased(n);
				}
			}

			saleDataA.add(n);
		}
		s.close();
		return saleDataA;
	}

	public static void loadSaleItem(String fileName, List<Item> items, List<Sale> saleResult, List<Person> people,List<Store>storeList) {
		// List<SaleItem> itemDataA = new ArrayList<>();
		Scanner s = null;
		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		while (s.hasNext()) {
			String line = s.nextLine();
			String tokens[] = line.split(",");

			Item i = null;

			for (Item item : items) {

				if (tokens[1].equals(item.getItemCode())) {

					if (item instanceof Product) {
						if (tokens.length == 2) {
							i = new Purchased(item.getItemCode(), item.getItemName(), item.getBasePrice());
						} else if (tokens.length == 4) {
							i = new LeasedProduct(item.getItemCode(), item.getItemName(), item.getBasePrice(),
									LocalDate.parse(tokens[2]), LocalDate.parse(tokens[3]));
						}

					} else if (item instanceof Service) {
						double numberOfHours = Double.parseDouble(tokens[2]);
						String personUUID = tokens[3];
						for (Person p : people) {
							if (p.getUUID().equals(personUUID)) {
								i = new Service(item.getItemCode(), item.getItemName(), item.getBasePrice(),
										numberOfHours, p);
							}
						}
					} else if (item instanceof Voice) {
						String phone = tokens[2];
						int noOfDaysPurchased = Integer.parseInt(tokens[3]);
						i = new Voice(item.getItemCode(), item.getItemName(), item.getBasePrice(), phone,
								noOfDaysPurchased);
					} else if (item instanceof Data) {
						Double noOfGBPurchased = Double.parseDouble(tokens[2]);
						i = new Data(item.getItemCode(), item.getItemName(), item.getBasePrice(), noOfGBPurchased);
					}

				}
			}

			for (Sale sale : saleResult) {
				if (tokens[0].equals(sale.getSaleCode()) && (i != null)) {
					sale.getAddItem(i);
				}
			}
			
//			for (Store store: storeList) {
//				for(Sale sale: saleResult) {
//					if(store.getStoreCode().equals(sale.getStore().getStoreCode())) {
//					store.getAddSale(sale);
//					}
//				}
					
				
			//}

		}
		s.close();
	}
}
