package com.MPKA_Group.demo.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String transactionId;
	private String transactionType;
	private BigDecimal amount;
	private String accountNumber;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Object object) {
		this.transactionType = (String) object;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(Object object) {
		this.amount = (BigDecimal) object;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Object object) {
		this.accountNumber = (String) object;
	}
	
	
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	public Transaction(String transactionId, String transactionType, BigDecimal amount, String accountNumber,
			String status) {
		super();
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.accountNumber = accountNumber;
		this.status = status;
	}
	
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}
	public LocalDate getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
