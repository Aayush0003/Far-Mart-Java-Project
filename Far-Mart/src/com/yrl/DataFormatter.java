/*
 * Author: Aayush Subedi
 * This class coverts the respective csv prints it in the json and xml files.
 */


package com.yrl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DataFormatter {
	
	//Convert and load the csv person class to json 
	public static void personJson(String outFile, List<Person>people) {
	
	 Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
	    PrintWriter pw2;

	    try  {
	    	pw2 = new PrintWriter(new File("data/Persons.json"));
	    	String json = gson1.toJson(people);
	    	JsonElement jsonElement = gson1.fromJson(json,JsonElement.class);
	    	String prettyJsonString = gson1.toJson(jsonElement);
	    	pw2.println(prettyJsonString);
	    	pw2.close();
	    	

	 

	    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
	    }

	    }
	
	
	//Convert and load the csv store class to json
   public static void storeJson(String outFile, List<Store>store) {
	   Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
	    PrintWriter pw3;
	    
	    try {
	    	pw3 = new PrintWriter(new File("data/Stores.json"));
	    	String json = gson2.toJson(store);
	    	JsonElement jsonElement = gson2.fromJson(json,JsonElement.class);
	    	String prettyJsonString = gson2.toJson(jsonElement);
	    	pw3.println(prettyJsonString);
	    	pw3.close();
	    } catch (FileNotFoundException e) {
	    	throw new RuntimeException(e);
	    }
   }
   
   //Convert and load the csv item class to json
   public static void itemJson(String outFile, List<Item>items) {
	   Gson gson3 = new GsonBuilder().setPrettyPrinting().create();
	    PrintWriter pw4;
	    
	    try {
	    	pw4 = new PrintWriter(new File("data/Items.json"));
	    	String json = gson3.toJson(items);
	    	JsonElement jsonElement = gson3.fromJson(json,JsonElement.class);
	    	String prettyJsonString = gson3.toJson(jsonElement);
	    	pw4.println(prettyJsonString);
	    	pw4.close();
	    } catch (FileNotFoundException e) {
	    	throw new RuntimeException(e);
	    }
   }
   
   //Convert and load the csv person class to xml
	public static void personXml(String outFile, List<Person>person) {
		XStream xstream = new XStream(new DomDriver());
		PrintWriter pwx1;
		try {
		String xml = xstream.toXML(person);
		pwx1 = new PrintWriter(new File("data/Persons.xml"));
		pwx1.println(xml);
		pwx1.close();
		
	} catch (FileNotFoundException e) {
    	throw new RuntimeException(e);
	}

	}
	
	 //Convert and load the csv store class to xml
	public static void storeXml(String outFile, List<Store>stores) {
		XStream xstream = new XStream(new DomDriver());
		PrintWriter pwx2;
		try {
		String xml = xstream.toXML(stores);
		pwx2 = new PrintWriter(new File("data/Stores.xml"));
		pwx2.println(xml);
		pwx2.close();
		
	} catch (FileNotFoundException e) {
    	throw new RuntimeException(e);
	}

	}
	
	 //Convert and load the csv item class to xml
	public static void itemXml(String outFile, List<Item>items) {
		XStream xstream = new XStream(new DomDriver());
		PrintWriter pwx3;
		try {
		String xml = xstream.toXML(items);
		pwx3 = new PrintWriter(new File("data/Items.xml"));
		pwx3.println(xml);
		pwx3.close();
		
	} catch (FileNotFoundException e) {
    	throw new RuntimeException(e);
	}

	}
	
}