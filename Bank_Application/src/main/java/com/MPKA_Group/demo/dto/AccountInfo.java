package com.MPKA_Group.demo.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountInfo {
	
	@Schema
	(
			name="User Bank Balance"
	)
    private BigDecimal accountBalance;
	
	
	@Schema
	(
			name="User Bank Account Number"
	)
    private String accountNumber;
	
	
	@Schema
	(
			name="User Bank Account Name"
	)
    private String accountName;
    
	
	
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
    
}
