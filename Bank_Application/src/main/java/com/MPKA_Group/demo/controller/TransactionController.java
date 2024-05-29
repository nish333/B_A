package com.MPKA_Group.demo.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.MPKA_Group.demo.domain.Transaction;
import com.MPKA_Group.demo.service.impl.BankStatement;
import com.itextpdf.text.DocumentException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/bankstatement")
public class TransactionController 
{
	private BankStatement bankStatement;
	
	@GetMapping("/print")
	public List<Transaction> generateBanStatement(@RequestParam String accountNumber,
									@RequestParam String startDate,@RequestParam String endDate) throws FileNotFoundException, DocumentException
	{
		return bankStatement.generateStatement(accountNumber, startDate, endDate);
	}
	
}
