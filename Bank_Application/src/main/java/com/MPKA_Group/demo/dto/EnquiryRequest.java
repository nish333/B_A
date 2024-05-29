package com.MPKA_Group.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnquiryRequest {
	
	private String accountNumber;

	public String getAccountNumber() {
		// TODO Auto-generated method stub
		return null;
	} 

}
