/*
 *  Author: Aayush Subedi
 *  This class loads the csv files and has the main that returns the values to the output files xml and json.
 *   
 */


 /*This main funtion loads the data from the dataformatter class and loads the
 *output file at the respective json and xml file
 */
//  public static void main(String[] args) {
//		
//		String personFile = "data/Persons.csv";
//	    String storeFile = "data/Stores.csv";
//	    String itemFile ="data/items.csv";
// String saleFile = "data/SaleItemtest.csv";
//
// List<SaleItem> sale = DataLoader.loadItem1(saleFile);
//
// DataLoader.loadItem1("data/saleitem.csv");
//	    
//		List<Person> people = DataLoader.loadPerson(personFile);
//		DataFormatter.personJson("data/Persons.json", people);
//		DataFormatter.personXml("data/Persons.xml", people);
//
//		List<Store> storeResult = DataLoader.loadStore(storeFile);
//		for(Store s: storeResult) {
//			System.out.println(s.getStoreCode());
//		
//
//		DataFormatter.storeJson("data/Stores.json", storeResult);
//		DataFormatter.storeXml("data/Stores.xml", storeResult);
//
//		List<Item> itemResult = DataLoader.loadItem(itemFile);
//		DataFormatter.itemJson("data/Items.json", itemResult);
//		DataFormatter.itemXml("data/Items.xml", itemResult);
//		
//
//	}