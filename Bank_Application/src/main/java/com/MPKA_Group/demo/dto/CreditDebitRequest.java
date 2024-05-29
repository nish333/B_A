package com.MPKA_Group.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDebitRequest {
	
	private String accountNumber;
	private BigDecimal amount;
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccountNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getAmount() {
		// TODO Auto-generated method stub
		return null;
	}

}
