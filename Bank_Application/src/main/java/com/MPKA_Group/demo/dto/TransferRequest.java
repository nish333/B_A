package com.MPKA_Group.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
	private String sourceAccountNumber;
	private String destinationAccountNumber;
	private BigDecimal amount;
	
	public String getSourceAccountNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDestinationAccountNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public BigDecimal getAmount() {
		// TODO Auto-generated method stub
		return amount;
	}
}
