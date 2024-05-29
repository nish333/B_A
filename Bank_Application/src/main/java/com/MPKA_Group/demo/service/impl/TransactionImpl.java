package com.MPKA_Group.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.MPKA_Group.demo.domain.Transaction;
import com.MPKA_Group.demo.dto.TransactionDto;
import com.MPKA_Group.demo.repository.TransactionsRepository;

@Component
public class TransactionImpl implements TransactionService {

    @Autowired
    TransactionsRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setAccountNumber(transactionDto.getAccountNumber());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setStatus("SUCCESS");
        
        transactionRepository.save(transaction);
        System.out.println("Transaction Saved Successfully...!");
    }
}


/*
public void saveTransaction(TransactionDto transactionDto) 
{
	Transaction transaction = Transaction.builder()
			.transactionType(transactionDto.getTransactionType())
			.accountNumber(transactionDto.getAccountNumber())
			.amount(transactionDto.getAmount())
			.status("SUCCESS")
			.build();
	transactionRepository.save(transaction);
	System.out.println("Transaction Saved Successfully...!");
	
}
*/
