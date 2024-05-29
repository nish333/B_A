package com.MPKA_Group.demo.utiles;

import java.time.Year;

public class AccountUtils {
	
	public static final String ACCOUNT_EXISTS_CODE="001";
	
	public static final String ACCOUNT_EXISTS_MESSAGE="This User Already have an Account...!!!";
	
	public static final String ACCOUNT_CREATION_SUCCESS="002";
	
	public static final String ACCOUNT_CREATION_MESSAGE="Account have been Successfully Created...!";

	public static final String ACCOUNT_NOT_EXIST_CODE="003";
	
	public static final String ACCOUNT_NOT_EXIST_MESSAGE="User with the provider Account Number DOES NOT EXIST";
	
	public static final String ACCOUNT_FOUND_CODE="004";
	
	public static final String ACCOUNT_FOUND_SUCCESS="User Account Found";
	
	public static final String ACCOUNT_CREDITED_SUCCESS="005";
			
	public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "Amount could be credited to Your Account Successfully...!";
	
	public static final String INSUFFICICENT_BALANCE_CODE="006";
	
	public static final String INSUFFICICENT_BALANCE_MESSAGE="Insufficient Balance";
	
	public static final String ACCOUNT_DEBITED_SUCCESS="007";
	
	public static final String ACCOUNT_DEBITED_SUCCESS_MESSAGE="Amount has been debited in your Account Successfully...!";
	
	public static final String TRANSFER_SUCCESSFULLY_CODE="008";
	
	public static final String TRANSFER_SUCCESSFULLY_MESSAGE="Transaction Completed successfully....!";
	
	
	public static String generateAccountNumber() {
		
		Year currentYear = Year.now();
		
		int min = 100000;
		
		int max = 99999;
		
		int randNumber = (int)Math.floor(Math.random() * (max - min + 1) + min);

		String year=String.valueOf(currentYear);
		String randomNumber = String.valueOf(randNumber);
		StringBuilder accountNumber = new StringBuilder();
		
		//return year+randomNumber;
		return accountNumber.append(year).append(randomNumber).toString();
	}
}
