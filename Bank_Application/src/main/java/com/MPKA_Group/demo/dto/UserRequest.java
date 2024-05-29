package com.MPKA_Group.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
	
	private String firstName;
	private String LastName;
	private String otherName;
	private String gender;
	private String address;
	private String stateOfOrigin;
	private String email;
	private String password;
	private String PhoneNumber;
	private String alternativePhoneNumber;
	
	public Object passwordEncoder;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getOtherName() {
		return otherName;
	}
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStateOfOrigin() {
		return stateOfOrigin;
	}
	public void setStateOfOrigin(String stateOfOrigin) {
		this.stateOfOrigin = stateOfOrigin;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getAlternativePhoneNumber() {
		return alternativePhoneNumber;
	}
	public void setAlternativePhoneNumber(String alternativePhoneNumber) {
		this.alternativePhoneNumber = alternativePhoneNumber;
	}
	public UserRequest(String firstName, String lastName, String otherName, String gender, String address,
			String stateOfOrigin, String email, String password, String phoneNumber, String alternativePhoneNumber) {
		super();
		this.firstName = firstName;
		LastName = lastName;
		this.otherName = otherName;
		this.gender = gender;
		this.address = address;
		this.stateOfOrigin = stateOfOrigin;
		this.email = email;
		this.password = password;
		PhoneNumber = phoneNumber;
		this.alternativePhoneNumber = alternativePhoneNumber;
	}
	
	
	public UserRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


}
