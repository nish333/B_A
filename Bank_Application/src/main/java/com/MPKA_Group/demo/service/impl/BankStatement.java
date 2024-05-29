package com.MPKA_Group.demo.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

import com.MPKA_Group.demo.domain.Transaction;
import com.MPKA_Group.demo.domain.User;
import com.MPKA_Group.demo.dto.EmailDetails;
import com.MPKA_Group.demo.repository.TransactionsRepository;
import com.MPKA_Group.demo.repository.UserRepository;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.AllArgsConstructor;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement extends SpringBootServletInitializer{
    
	@Autowired
	private TransactionsRepository transactionsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

	//private Log log;	
	
	private static Logger log = LogManager.getLogger(BankStatement.class);
	
	private static final String FILE="C:\\Users\\nishanth.s\\Downloads\\Bank Statement\\MyStatement.pdf";
	
	public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException
	{
		LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
		LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
		List<Transaction> transactionList = transactionsRepository.findAll().stream()
				.filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
				.filter(transaction -> transaction.getCreatedAt().isEqual(start))
				.filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();
		
		User user=userRepository.findByAccountNumber(accountNumber);
		String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();
 		Rectangle statementSize = new Rectangle(PageSize.A4);
		Document document = new Document(statementSize);
		log.info("Setting the sixe od the document");
		OutputStream outputStream = new FileOutputStream(FILE);
		PdfWriter.getInstance(document, outputStream);
		document.open();
		
		
		PdfPTable bankInfoTable = new PdfPTable(1);                            //numColumns : 1
		PdfPCell bankName = new PdfPCell(new Phrase("MPKA GROUPS OF BANK"));
		bankName.setBorder(0);
		bankName.setPadding(20f);
		bankName.setBackgroundColor(BaseColor.BLUE);
		
		PdfPCell bankAddress = new PdfPCell(new Phrase("NO:333,Dubai Kuruku Santhu,Asia"));
		bankAddress.setBorder(0);
		bankInfoTable.addCell(bankName);
		bankInfoTable.addCell(bankAddress);
		
		
		PdfPTable statementInfo = new PdfPTable(2);                              //numColumns : 2
		PdfPCell customerInfo = new PdfPCell(new Phrase("Start date : "+ startDate));
		customerInfo.setBorder(0);
		PdfPCell statement = new PdfPCell(new Phrase("BANK STATEMENT"));
		statement.setBorder(0);
		PdfPCell stopDate = new PdfPCell(new Phrase("End date : "+ endDate));
		stopDate.setBorder(0);
		
		PdfPCell name= new PdfPCell(new Phrase("Customer Name : "+ customerName));
		name.setBorder(0);
		PdfPCell space = new PdfPCell();
		space.setBorder(0);
		PdfPCell address = new PdfPCell(new Phrase("Customer Address : "+user.getAddress()));
		address.setBorder(0);
		
		PdfPTable transactionsTable = new PdfPTable(4);                   //numColumns : 4
		PdfPCell date = new PdfPCell(new Phrase("DATE"));
		date.setBackgroundColor(BaseColor.BLUE);
		date.setBorder(0);
		PdfPCell transactionsType = new PdfPCell(new Phrase("TRANSACTION TYPE"));
		transactionsType.setBackgroundColor(BaseColor.BLUE);
		transactionsType.setBorder(0);
		PdfPCell transactionsAmount = new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
		transactionsAmount.setBackgroundColor(BaseColor.BLUE);
		transactionsAmount.setBorder(0);
		PdfPCell status = new PdfPCell(new Phrase("STATUS"));
		status.setBackgroundColor(BaseColor.BLUE);
		status.setBorder(0);
		
		transactionsTable.addCell(date);
		transactionsTable.addCell(transactionsType);
		transactionsTable.addCell(transactionsAmount);
		transactionsTable.addCell(status);
		
		transactionList.forEach(transaction ->{
			transactionsTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
			transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
			transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
			transactionsTable.addCell(new Phrase(transaction.getStatus()));	
		});
		
		
		statementInfo.addCell(customerInfo);
		statementInfo.addCell(statement);
		statementInfo.addCell(endDate);
		statementInfo.addCell(name);
		statementInfo.addCell(space);
		statementInfo.addCell(address);
		
		document.add(bankInfoTable);
		document.add(statementInfo);
		document.add(transactionsTable);
		
		document.close();
		
//		EmailDetails emailDetails = EmailDetails.builder()
//				.recipient(user.getEmail())
//				.subject("STATEMENT OF THE ACCOUNT")
//				.messageBody("kindly find your requested account statement attached...!")
//				.attachment(FILE)
//				.build();	
//		
//		emailService.sendEmailWithAttachment(emailDetails);
		
		
		EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setSubject("STATEMENT OF THE ACCOUNT");
        emailDetails.setMessageBody("Kindly find your requested account statement attached...!");
        emailDetails.setAttachment(FILE);
        
        emailService.sendEmailWithAttachment(emailDetails);
    
        return transactionList;	
	
	}

}
