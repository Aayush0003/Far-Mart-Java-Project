/*
 *  Author: Aayush Subedi
 *  This class calls the data required for the person file. 
 *  It also has the constructors and getters.
 */

package com.yrl;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private String UUID;
	private String firstName;
	private String lastName;
	private Address address;
	private List<String> emailList;

	public Person(String uUID, String firstName, String lastName, Address address, List<String> emailList) {
		UUID = uUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emailList = new ArrayList<>();
	}

	public Person(String uUID, String firstName, String lastName, Address address) {
		UUID = uUID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		this.UUID = uUID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	public void addEmail(String email) {
		this.emailList.add(email);
	}

	
	public String getName() {
		return lastName +", "+ firstName;
	}
	
	public String getNameinorder() {
		return firstName +","+lastName;
	}
	
//	public void printEmail() {
//		for(int i = 0; i<emailList.size();i++) {
//			System.out.printf("%s, ");
//			
//		}
//		System.out.printf("%s",emailList.get(emailList.size()));
//
//	}
	
//    public String getEmailsAsString() {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < emailList.size(); i++) {
//            stringBuilder.append(emailList.get(i));
//            if (i < emailList.size() - 1) {
//                stringBuilder.append(", "); // Add comma and space for all elements except the last one
//            }
//        }
//        return stringBuilder.toString();
    }
	
	

