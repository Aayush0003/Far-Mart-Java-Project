package com.yrl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

//import unl.soc.database.DatabaseInfo;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class SalesData {

	/**
	 * Removes all records from all tables in the database.
	 */
	public static void clearDatabase() {
		// TODO: implement
		try (Connection conn = ConnectionFactory.connection()) {
	        // Delete all records from the Sale table
	        String query = "delete from SaleItem";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }

	        // Delete all records from the Email table
	        query = "delete from Item";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }

	        // Delete all records from the Person table
	        query = "delete from Sale";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }

	        // Delete all records from the Address table
	        query = "delete from Store";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }

	        // Delete all records from the Item table
	        query = "delete from Email";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }
	        
	        query = "delete from Manager";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }

	        // Delete all records from the Store table
	        query = "delete from Person";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }
	        query = "delete from Address";
	        try (PreparedStatement ps = conn.prepareStatement(query)) {
	            ps.executeUpdate(query);
	        }
	    } catch (SQLException e) {
	        System.out.println("SQLException: ");
	        e.printStackTrace();
	        throw new RuntimeException(e);
	    }
		
	}

	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personUuid
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 */
	public static void addPerson(String personUuid, String firstName, String lastName, String street, String city,
			String state, String zip) {
		// TODO: implement
		int addressId = 0;
		int personId = 0;

		try (Connection conn = ConnectionFactory.connection()) {
			String query = "select addressId from Address where street = ? and city = ? and state = ? and zip = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						addressId = rs.getInt("addressId");
					} else {
						String query2 = "insert into Address(street,city,state,zip) values(?,?,?,?)";

						try (PreparedStatement ps2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
							ps2.setString(1, street);
							ps2.setString(2, city);
							ps2.setString(3, state);
							ps2.setString(4, zip);
							ps2.executeUpdate();

							try (ResultSet keys = ps2.getGeneratedKeys()) {
								if (keys.next()) {
									addressId = keys.getInt(1);
								}
							}
						}
					}
				}
			}

			String query2 = "insert into Person (personCode,lastName,firstName,addressId) values(?,?,?,?)";
			try (PreparedStatement ps2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
				ps2.setString(1, personUuid);
				ps2.setString(2, firstName);
				ps2.setString(3, lastName);
				;
				ps2.setInt(4, addressId);
				ps2.executeUpdate();

				try (ResultSet key = ps2.getGeneratedKeys()) {
					if (key.next()) {
						personId = key.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personUuid</code>
	 *
	 * @param personUuid
	 * @param email
	 */
	public static void addEmail(String personUuid, String email) {
		// TODO: implement
		 int personId = 0;

		    try (Connection conn = ConnectionFactory.connection()) {
		        String query = "select personId from Person where personCode = ?";
		        try (PreparedStatement ps = conn.prepareStatement(query)) {
		            ps.setString(1, personUuid);

		            try (ResultSet rs = ps.executeQuery()) {
		                if (rs.next()) {
		                    personId = rs.getInt("personId");
		                } else {
		                    String query2 = "insert into Person (personCode) values(?)";
		                    try (PreparedStatement ps2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
		                        ps2.setString(1, personUuid);
		                        ps2.executeUpdate();

		                        try (ResultSet key = ps2.getGeneratedKeys()) {
		                            if (key.next()) {
		                                personId = key.getInt(1);
		                            }
		                        }
		                    }
		                }
		            }
		        }

		        String query2 = "insert into Email (personId, emailAddress) values(?, ?)";
		        try (PreparedStatement ps2 = conn.prepareStatement(query2)) {
		            ps2.setInt(1, personId);
		            ps2.setString(2, email);
		            ps2.executeUpdate();
		        }
		    } catch (SQLException e) {
		        System.out.println("SQLException: ");
		        e.printStackTrace();
		        throw new RuntimeException(e);
		    }
	}

	public static int getpersonId(String personCode) {
		int personId = 0;

		try (Connection conn = ConnectionFactory.connection()) {
			String query = "select personId from Person where personCode = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, personCode);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						personId = rs.getInt("personId");
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return personId;
	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 *
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip) {
		// TODO: implement
		int addressId = 0;
		int managerId = getpersonId(managerCode);
		int storeId = 0;

		try (Connection conn = ConnectionFactory.connection()) {
			String query = "select addressId from Address where street = ? and city = ? and state = ? and zip = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setString(3, state);
				ps.setString(4, zip);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						addressId = rs.getInt("addressId");
					} else {
						String query2 = "insert into Address(street,city,state,zip) values(?,?,?,?)";

						try (PreparedStatement ps2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
							ps2.setString(1, street);
							ps2.setString(2, city);
							ps2.setString(3, state);
							ps2.setString(4, zip);
							ps2.executeUpdate();

							try (ResultSet keys = ps2.getGeneratedKeys()) {
								if (keys.next()) {
									addressId = keys.getInt(1);
								}
							}
						}
					}
				}
			}

			String query2 = "insert into Store (storeCode,managerId,addressId) values(?,?,?)";
			try (PreparedStatement ps2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
				ps2.setString(1, storeCode);
				ps2.setInt(2, managerId);
				ps2.setInt(3, addressId);
				ps2.executeUpdate();

				try (ResultSet key = ps2.getGeneratedKeys()) {
					if (key.next()) {
						storeId = key.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an item record to the database of the given <code>type</code> with the
	 * given <code>code</code>, <code>name</code> and <code>basePrice</code>.
	 *
	 * Valid values for the <code>type</code> will be <code>"Product"</code>,
	 * <code>"Service"</code>, <code>"Data"</code>, or <code>"Voice"</code>.
	 *
	 * @param itemCode
	 * @param name
	 * @param type
	 * @param basePrice
	 */
	public static void addItem(String code, String name, String type, double basePrice) {
		// TODO: implement
		int itemId = 0;
		try (Connection conn = ConnectionFactory.connection()) {
			String query = "insert into Item (itemCode,name,type,baseCost) values(?,?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, code);
				ps.setString(2, name);
				ps.setString(3, type);
				ps.setDouble(4, basePrice);
				ps.executeUpdate();

				try (ResultSet key = ps.getGeneratedKeys()) {
					if (key.next()) {
						itemId = key.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	public static int getItemId(String itemCode) {
		int itemId = 0;

		try (Connection conn = ConnectionFactory.connection()) {
			String query = "select itemId from Item where itemCode = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, itemCode);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						itemId = rs.getInt("itemId");
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return itemId;
	}
	
	public static int getstoreId(String storeCode) {
		int storeId = 0;

		try (Connection conn = ConnectionFactory.connection()) {
			String query = "select storeId from Store where storeCode = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, storeCode);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						storeId = rs.getInt("storeId");
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return storeId;
	}

	/**
	 * Adds an Sale record to the database with the given data.
	 *
	 * @param saleCode
	 * @param storeCode
	 * @param customerPersonUuid
	 * @param salesPersonUuid
	 * @param saleDate
	 */
	public static void addSale(String saleCode, String storeCode, String customerPersonUuid, String salesPersonUuid,
			String saleDate) {
		// TODO: implement
		int addressId = 0;
		int saleId = 0;
		int customerId = getpersonId(customerPersonUuid);
		int storeId = getstoreId(storeCode);
		int salesPersonId = getpersonId(salesPersonUuid);

		try (Connection conn = ConnectionFactory.connection()) {
			
		   String query = "insert into Sale (saleCode,saleDate,customerId,salesPersonId,storeId) values(?,?,?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, saleCode);
				ps.setString(2, saleDate);
				ps.setInt(3, customerId);
				ps.setInt(4, salesPersonId);
				ps.setInt(5, storeId);
				ps.executeUpdate();

				try (ResultSet key = ps.getGeneratedKeys()) {
					if (key.next()) {
						saleId = key.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static int getSaleId(String saleCode) {
		int saleId = 0;

		try (Connection conn = ConnectionFactory.connection()) {
			String query = "select saleId from Sale where saleCode = ?";
			try (PreparedStatement ps = conn.prepareStatement(query)) {
				ps.setString(1, saleCode);

				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						saleId = rs.getInt("saleId");
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return saleId;
	}

	/**
	 * Adds a particular product (identified by <code>itemCode</code>) to a
	 * particular sale (identified by <code>saleCode</code>).
	 *
	 * @param saleCode
	 * @param itemCode
	 */
	public static void addProductToSale(String saleCode, String itemCode) {
		// TODO: implement
		int saleId = getSaleId(saleCode);
		int itemId = getItemId(itemCode);
	

		try (Connection conn = ConnectionFactory.connection()) {
			
		   String query = "insert into SaleItem (saleId,itemId) values(?,?)";
			try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, saleId);
				ps.setInt(2, itemId);
				
				ps.executeUpdate();

				try (ResultSet key = ps.getGeneratedKeys()) {
					if (key.next()) {
						//saleId = key.getInt(1);
						itemId = key.getInt(1);
						
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

	}

	/**
	 * Adds a particular leased (identified by <code>itemCode</code>) to a
	 * particular sale (identified by <code>saleCode</code>) with the start/end date
	 * specified.
	 *
	 * @param saleCode
	 * @param startDate
	 * @param endDate
	 */
	public static void addLeaseToSale(String saleCode, String itemCode, String startDate, String endDate) {
		// TODO: implement
		int saleId = getSaleId(saleCode);
		int itemId = getItemId(itemCode);
	

		try (Connection conn = ConnectionFactory.connection()) {
			
		   String query = "insert into SaleItem (saleId,itemId,startLeaseDate,endLeaseDate) values(?,?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, saleId);
				ps.setInt(2, itemId);
				ps.setString(3,startDate);
				ps.setString(4,endDate);
				
				ps.executeUpdate();

				try (ResultSet key = ps.getGeneratedKeys()) {
					if (key.next()) {
						//saleId = key.getInt(1);
						itemId = key.getInt(1);
						//startDate = key.getString(3);
						//endDate = key.getString(4);
						
						
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular sale (identified by <code>saleCode</code>) with the specified
	 * number of hours. The service is done by the employee with the specified
	 * <code>servicePersonUuid</code>
	 *
	 * @param saleCode
	 * @param itemCode
	 * @param billedHours
	 * @param servicePersonUuid
	 */
	public static void addServiceToSale(String saleCode, String itemCode, double billedHours,
			String servicePersonUuid) {
		// TODO: implement
		int saleId = getSaleId(saleCode);
		int itemId = getItemId(itemCode);
		int servicePersonId = getpersonId(servicePersonUuid);
		
		try (Connection conn = ConnectionFactory.connection()) {
				
		   String query = "insert into SaleItem (saleId,itemId,hoursBilled,employeeId) values(?,?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, saleId);
				ps.setInt(2, itemId);
				ps.setDouble(3,billedHours);
				ps.setInt(4,servicePersonId);
				
				ps.executeUpdate();

				try (ResultSet key = ps.getGeneratedKeys()) {
					if (key.next()) {
						//saleId = key.getInt(1);
						itemId = key.getInt(1);
						//billedHours = key.getDouble(3);
						//servicePersonId = key.getInt(4);
						
						
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	

	/**
	 * Adds a particular data plan (identified by <code>itemCode</code>) to a
	 * particular sale (identified by <code>saleCode</code>) with the specified
	 * number of gigabytes.
	 *
	 * @param saleCode
	 * @param itemCode
	 * @param gbs
	 */
	public static void addDataPlanToSale(String saleCode, String itemCode, double gbs) {
		// TODO: implement

		int saleId = getSaleId(saleCode);
		int itemId = getItemId(itemCode);
		
		
		try (Connection conn = ConnectionFactory.connection()) {
				
		   String query = "insert into SaleItem (saleId,itemId,noOfGBPurchased) values(?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, saleId);
				ps.setInt(2, itemId);
				ps.setDouble(3,gbs);
			
				
				ps.executeUpdate();

				try (ResultSet key = ps.getGeneratedKeys()) {
					if (key.next()) {
						//saleId = key.getInt(1);
						itemId = key.getInt(1);
						//gbs = key.getDouble(3);
						
						
						
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
		
	

	/**
	 * Adds a particular voice plan (identified by <code>itemCode</code>) to a
	 * particular sale (identified by <code>saleCode</code>) with the specified
	 * <code>phoneNumber</code> for the given number of <code>days</code>.
	 *
	 * @param saleCode
	 * @param itemCode
	 * @param phoneNumber
	 * @param days
	 */
	public static void addVoicePlanToSale(String saleCode, String itemCode, String phoneNumber, int days) {
		// TODO: implement
		
		int saleId = getSaleId(saleCode);
		int itemId = getItemId(itemCode);
		
		
		try (Connection conn = ConnectionFactory.connection()) {
				
		   String query = "insert into SaleItem (saleId,itemId,phone,noOfDays) values(?,?,?,?)";
			try (PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, saleId);
				ps.setInt(2, itemId);
				ps.setString(3,phoneNumber);
				ps.setInt(4,days);
				
				ps.executeUpdate();

				try (ResultSet key = ps.getGeneratedKeys()) {
					if (key.next()) {
						//saleId = key.getInt(1);
						itemId = key.getInt(1);
						//phoneNumber = key.getString(3);
						//days = key.getInt(4);
						
						
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	}
	
	
	
	