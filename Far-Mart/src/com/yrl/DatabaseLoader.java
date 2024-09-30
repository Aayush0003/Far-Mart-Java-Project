package com.yrl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class DatabaseLoader {

	
	//private static Logger logger = Logger.getLogger(DatabaseLoader.class.getName());

	public static List<Person> loadPerson() {

		List<Person> PersonA = new ArrayList<>();

		String personQuery = "select addressId,personId,personCode, firstName, lastName from Person";

		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(personQuery);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("personId");
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int addressId = rs.getInt("addressId");

				// Address address = Address.get(addressId);

				Person person = new Person(personCode, firstName, lastName, dataAddressLoader(addressId),
						dataEmailLoader(personId));
				// personMap.put(personId, person);
				PersonA.add(person);
			}
			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("Person Retrieval Success");
			// PersonA.add(person);
		} catch (SQLException e) {
			//logger.error("Person Retrieval Failure");
			e.printStackTrace();
		}
		

		return PersonA;
	}

	public static List<String> dataEmailLoader(int personId) {
		List<String> emailAddresses = new ArrayList<>();

		String emailQuery = "select emailAddress from Email where personId = ?";
		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement emailStatement = conn.prepareStatement(emailQuery);
			emailStatement.setInt(1, personId);
			ResultSet rs = emailStatement.executeQuery();

			while (rs.next()) {
				String emailAddress = rs.getString("emailAddress");
				emailAddresses.add(emailAddress);
			}
			rs.close();
			emailStatement.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("Email Retrieval Success");
		} catch (SQLException e) {
			//logger.error("Email Retrieval Failure");
			e.printStackTrace();
		}
		return emailAddresses;
	}

	public static Address dataAddressLoader(int addressId) {
		// List<String> AddressA = new ArrayList<>();

		String addressQuery = "select addressId, street, city, state, zip from Address where addressId = ?";
		Address address = null;
		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(addressQuery);
			ps.setInt(1, addressId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String street = rs.getString("street");
				String city = rs.getString("city");
				String zip = rs.getString("zip");
				String state = rs.getString("state");
				address = new Address(street, city, state, zip);

			}
			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("Address Retrieval Success");
		} catch (SQLException e) {
			//logger.info("Address Retrieval Success");
			e.printStackTrace();
		}
		return address;
	}

	public static List<Item> loadItem() {

		List<Item> saleItemA = new ArrayList<>();

		String itemQuery = "select itemId, type, itemCode, name, baseCost from Item";

		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(itemQuery);
			ResultSet rs = ps.executeQuery();
			//Item item = null;

			while (rs.next()) {

				String itemCode = rs.getString("itemCode");
				String itemType = rs.getString("type");
				String itemName = rs.getString("name");
				Double basePrice = rs.getDouble("baseCost");

				if (itemType.equals("Product")) {
					saleItemA.add(new Product(itemCode, itemName, basePrice));
				} else if (itemType.equals("Service")) {

					saleItemA.add(new Service(itemCode, itemName, basePrice));

				} else if (itemType.equals("Data")) {
					saleItemA.add(new Data(itemCode, itemName, basePrice));

				} else if (itemType.equals("Voice")) {
					saleItemA.add(new Voice(itemCode, itemName, basePrice));
				}

			}
			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("Item Retrieval Success");

		} catch (SQLException e) {
			//logger.info("Item Retrieval Success");
			e.printStackTrace();
		}
		return saleItemA;

	}

	public static Person dataPersononID(int personId) {

		Person p = null;
		String personQuery = "select addressId,personId,personCode, firstName, lastName from Person where personId = ?";

		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(personQuery);
			ps.setInt(1, personId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String personCode = rs.getString("personCode");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				int addressId = rs.getInt("addressId");

				p = new Person(personCode, firstName, lastName, dataAddressLoader(addressId));

			}

			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("Person Retrieval Success");
		} catch (SQLException e) {
			//logger.info("Person Retrieval Success");
			e.printStackTrace();
		}
		return p;
	}

	public static List<Store> loadStore(List<Person> people) {

		List<Store> storeDataA = new ArrayList<>();

		String storeQuery = "select storeCode, managerId, addressId from Store";

		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(storeQuery);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String storeCode = rs.getString("storeCode");
				int managerId = rs.getInt("managerId");
				int addressId = rs.getInt("addressId");

				Store s = new Store(storeCode, dataPersononID(managerId), dataAddressLoader(addressId));

				storeDataA.add(s);
			}
			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("Store Retrieval Success");
		} catch (SQLException e) {
			//logger.info("Store Retrieval Success");
			e.printStackTrace();
		}
		return storeDataA;

	}

	public static Store loadStoreonId(int StoreId) {

		Store s = null;

		String storeQuery = "select storeCode, managerId, addressId from Store where storeId = ?";

		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(storeQuery);
			ps.setInt(1, StoreId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String storeCode = rs.getString("storeCode");
				int managerId = rs.getInt("managerId");
				int addressId = rs.getInt("addressId");

				s = new Store(storeCode, dataPersononID(managerId), dataAddressLoader(addressId));
			}
			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("StoreonId Retrieval Success");
		} catch (SQLException e) {
			//logger.info("StoreonId Retrieval Success");
			e.printStackTrace();
		}
		return s;

	}

	public static List<Sale> loadSale(List<Store> storeList, List<Person> personList) {
		List<Sale> saleDataA = new ArrayList<>();

		String SaleQuery = "select saleCode, storeId, customerId, salesPersonId, saleDate from Sale";

		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(SaleQuery);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String saleCode = rs.getString("saleCode");
				int storeId = rs.getInt("storeId");
				int customerId = rs.getInt("customerId");
				int salePersonId = rs.getInt("salesPersonId");
				LocalDate saleDate = LocalDate.parse(rs.getString("saleDate"));
				//Store i = loadStoreonId(storeId);

				String StoreCode = loadStoreonId(storeId).getStoreCode();
				for (Store store : storeList) {
					if (StoreCode.equals(store.getStoreCode())) {
						Sale s = new Sale(saleCode, store, dataPersononID(customerId),
								dataPersononID(salePersonId), saleDate);
						store.addPurchased(s);
						saleDataA.add(s);
;
					}
				}
				
//				Sale s = new Sale(saleCode, StoreCode, dataPersononID(customerId),
//						dataPersononID(salePersonId), saleDate);
//
//				saleDataA.add(s);
				
				
				

				
			}
			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("Sale Retrieval Success");
		} catch (SQLException e) {
			//logger.info("Sale Retrieval Success");
			e.printStackTrace();
		}
		return saleDataA;

	}

	public static void loadSaleItem(List<Item> items, List<Sale> saleResult, List<Person> people) {
		// List<Item> loadsaleItem = new ArrayList<>();
		String SaleItemQuery = "select Sale.saleId, ItemCode, saleCode, hoursBilled, employeeId, startLeaseDate,endLeaseDate,noofGBPurchased,phone,noOfDays from SaleItem Join Item On Item.itemId = SaleItem.itemid Join Sale on Sale.saleId = SaleItem.saleId ";

		try {
			Connection conn = ConnectionFactory.connection();
			PreparedStatement ps = conn.prepareStatement(SaleItemQuery);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int saleId = rs.getInt("saleId");
				String ItemCode = rs.getString("ItemCode");
				String saleCode = rs.getString("saleCode");
				String hoursBilled =rs.getString("hoursBilled");
				int employeeId = rs.getInt("employeeId");
				String startLeaseDate = rs.getString("startLeaseDate");
				String endLeaseDate = rs.getString("endLeaseDate");
				String noofGBPurchased = rs.getString("noofGBPurchased");
				String phone = rs.getString("phone");
				String noOfDays = rs.getString("noOFDays");

				Item i = null;
				for (Item item : items) {
					if (ItemCode.equals(item.getItemCode())) {
						i = item;

					}
				}
				Item itemOne = null;

				if (i instanceof Product) {

					if (startLeaseDate != null) {
						itemOne = new LeasedProduct(i.getItemCode(), i.getItemName(), i.getBasePrice(), LocalDate.parse(startLeaseDate),
								LocalDate.parse(endLeaseDate));
					} else {
						itemOne = new Purchased(i.getItemCode(), i.getItemName(), i.getBasePrice());

					}
				} else if (i instanceof Service) {
				if(hoursBilled != null) {
					itemOne = new Service(i.getItemCode(), i.getItemName(), i.getBasePrice(), Double.parseDouble(hoursBilled),
							dataPersononID(employeeId));
					}

				} else if (i instanceof Data) {
					if(noofGBPurchased != null) {
					itemOne = new Data(i.getItemCode(), i.getItemName(), i.getBasePrice(), Double.parseDouble(noofGBPurchased));
					}
				} else if (i instanceof Voice) {
					if(noOfDays != null) {
					itemOne = new Voice(i.getItemCode(), i.getItemName(), i.getBasePrice(), phone, Integer.parseInt(noOfDays));
				}
				}
				Sale s2 = null;
				for (Sale s : saleResult) {
					if (saleCode.equals(s.getSaleCode())) {
						s2 = s;
					}
				}
				s2.getAddItem(itemOne);
			}

			rs.close();
			ps.close();
			ConnectionFactory.closeConnection(conn);
			//logger.info("SaleItem Retrieval Success");
		} catch (SQLException e) {
			//logger.info("SaleItem Retrieval Success");
			e.printStackTrace();

		}
	}

}
