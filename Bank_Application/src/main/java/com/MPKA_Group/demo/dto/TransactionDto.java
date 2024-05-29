package com.MPKA_Group.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
	
	private String transactionType;
	private BigDecimal amount;
	private String accountNumber;
	private String status;
	
	public Object getAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAccountNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAccountNumber(String accountNumber2) {
		// TODO Auto-generated method stub
		
	}

	public void setAmount(BigDecimal amount2) {
		// TODO Auto-generated method stub
		
	}

	public void setTransactionType(String string) {
		// TODO Auto-generated method stub
		
	}
	

}
