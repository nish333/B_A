package com.MPKA_Group.demo.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.MPKA_Group.demo.dto.AccountInfo;
import com.MPKA_Group.demo.utiles.AccountUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;

import com.MPKA_Group.demo.config.JwtTokenProvider;
import com.MPKA_Group.demo.domain.Role;
import com.MPKA_Group.demo.domain.User;
import com.MPKA_Group.demo.dto.BankResponse;
import com.MPKA_Group.demo.dto.CreditDebitRequest;
import com.MPKA_Group.demo.dto.EmailDetails;
import com.MPKA_Group.demo.dto.EnquiryRequest;
import com.MPKA_Group.demo.dto.LoginDto;
import com.MPKA_Group.demo.dto.TransactionDto;
import com.MPKA_Group.demo.dto.TransferRequest;
import com.MPKA_Group.demo.dto.UserRequest;
import com.MPKA_Group.demo.repository.UserRepository;

@AllArgsConstructor
@Service
@Component
public class UserServiceImpl implements UserService 
{
	
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    EmailService emailService;
    
    @Autowired
    TransactionService transactionService;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
	@Override
	public BankResponse createAccount(UserRequest userRequest) 
	{
		
		//Create an account -->save the new user into database
		//Check the user already have an account
		
		if(userRepository.existsByEmail(userRequest.getEmail()))
		{
			BankResponse response = new BankResponse();
			response.setResponseCode(AccountUtils.ACCOUNT_EXISTS_CODE);
			response.setResponseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE);
			response.setAccountInfo(null);
			return response;
		}
		
		// Create and populate User object
		User newUser = new User();
		newUser.setFirstName(userRequest.getFirstName());
		newUser.setLastName(userRequest.getLastName());
		newUser.setOtherName(userRequest.getOtherName());
		newUser.setGender(userRequest.getGender());
		newUser.setAddress(userRequest.getAddress());
		newUser.setStateOfOrigin(userRequest.getStateOfOrigin());
		newUser.setAccountNumber(AccountUtils.generateAccountNumber());
		newUser.setAccountBalance(BigDecimal.ZERO);
		newUser.setEmail(userRequest.getEmail());
			String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
			newUser.setPassword(encodedPassword);
		newUser.setPhoneNumber(userRequest.getPhoneNumber());
		newUser.setAlternativePhoneNumber(userRequest.getAlternativePhoneNumber());
		newUser.setStatus("ACTIVE");
		newUser.setRole(Role.valueOf("ROLE_ADMIN"));
		//newUser.setRole("ROLE_ADMIN".toString());
		//newUser.setRole("ROLE_ADMIN");

	User savedUser = userRepository.save(newUser);
	
	//Sent an EmailAlert
	// Create and populate EmailDetails object
	EmailDetails emailDetails = new EmailDetails();
	emailDetails.setRecipient(savedUser.getEmail());
	emailDetails.setSubject("Bank Account Creation");
	emailDetails.setMessageBody("Congratulations...! Your Account has been Created Successfully.\n Your Account Details  \n"
	        + "Account Name: " + savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName()
	        + "\nAccount Number: " + savedUser.getAccountNumber());

	// Send email alert
	emailService.sendEmailAlert(emailDetails);

	// Create and populate AccountInfo object
	AccountInfo accountInfo = new AccountInfo();
	accountInfo.setAccountBalance(savedUser.getAccountBalance());
	accountInfo.setAccountNumber(savedUser.getAccountNumber());
	accountInfo.setAccountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName());

	// Create and return BankResponse object
	BankResponse bankResponse = new BankResponse();
	bankResponse.setResponseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS);
	bankResponse.setResponseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE);
	bankResponse.setAccountInfo(accountInfo);

	return bankResponse;

  }
	
	/*
	public BankResponse login(LoginDto loginDto)
	{
		org.springframework.security.core.Authentication authentication = null; 
		authentication = (org.springframework.security.core.Authentication) authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		EmailDetails loginAlert = EmailDetails.builder()
				.subject("You're Logged in...")
				.recipient(loginDto.getEmail())
				.messageBody("You logged into your account. If you did not initiate this request, Please contact Your Bank.")
				.build();
		
		emailService.sendEmailAlert(loginAlert);
		
		return BankResponse.builder()
				.responseCode("Login Successfully...")
				.responseMessage(jwtTokenProvider.generateToken(authentication))
				.build();
	}
	*/
	
	
	 public BankResponse login(LoginDto loginDto) {
	        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
	        );
	        
	     // Set the authenticated user into the SecurityContext
	       SecurityContextHolder.getContext().setAuthentication(authentication);

	        EmailDetails loginAlert = new EmailDetails();
	        loginAlert.setSubject("You're Logged in...");
	        loginAlert.setRecipient(loginDto.getEmail());
	        loginAlert.setMessageBody("You logged into your account. If you did not initiate this request, please contact your bank.");

	        emailService.sendEmailAlert(loginAlert);

	        BankResponse response = new BankResponse();
	        response.setResponseCode("Login Successfully...");
	        response.setResponseMessage(jwtTokenProvider.generateToken(authentication));

	        return response;
	    }
	
	

	@Override
	public BankResponse balanceEnquiry(EnquiryRequest request) {
	    boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
	    if (!isAccountExist) {
	        BankResponse response = new BankResponse();
	        response.setResponseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE);
	        response.setResponseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
	        response.setAccountInfo(null);
	        return response;
	    }
	    
	    User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());

	    AccountInfo accountInfo = new AccountInfo();
	    accountInfo.setAccountBalance(foundUser.getAccountBalance());
	    accountInfo.setAccountNumber(foundUser.getAccountNumber());
	    accountInfo.setAccountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName());

	    BankResponse response = new BankResponse();
	    response.setResponseCode(AccountUtils.ACCOUNT_FOUND_CODE);
	    response.setResponseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS);
	    response.setAccountInfo(accountInfo);
	    
	    return response;
	}


	@Override
	public String nameEnquiry(EnquiryRequest request) {
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExist)
		{
			return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
		}
		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
	}
	
	
	@Override
	public BankResponse creditAccount(CreditDebitRequest request) {
	    boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
	    if (!isAccountExist) {
	        BankResponse response = new BankResponse();
	        response.setResponseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE);
	        response.setResponseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
	        response.setAccountInfo(null);
	        return response;
	    }
	    
	    User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
	    userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
	    userRepository.save(userToCredit);
	    
	    // Save Transaction
	    TransactionDto transactionDto = new TransactionDto();
	    transactionDto.setAccountNumber(userToCredit.getAccountNumber());
	    transactionDto.setTransactionType("CREDIT");
	    transactionDto.setAmount(request.getAmount());
	    transactionService.saveTransaction(transactionDto);
	    
	    // Create AccountInfo object
	    AccountInfo accountInfo = new AccountInfo();
	    accountInfo.setAccountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName());
	    accountInfo.setAccountBalance(userToCredit.getAccountBalance());
	    accountInfo.setAccountNumber(request.getAccountNumber());
	    
	    // Create and return BankResponse object
	    BankResponse response = new BankResponse();
	    response.setResponseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS);
	    response.setResponseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE);
	    response.setAccountInfo(accountInfo);
	    
	    return response;
	}


	@Override
	public BankResponse debitAccount(CreditDebitRequest request) {
	    boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
	    if (!isAccountExist) {
	        BankResponse response = new BankResponse();
	        response.setResponseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE);
	        response.setResponseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
	        response.setAccountInfo(null);
	        return response;
	    }
	    
	    User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
	    BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
	    BigInteger debitAmount = request.getAmount().toBigInteger();
	    if (availableBalance.intValue() < debitAmount.intValue()) {
	        BankResponse response = new BankResponse();
	        response.setResponseCode(AccountUtils.INSUFFICICENT_BALANCE_CODE);
	        response.setResponseMessage(AccountUtils.INSUFFICICENT_BALANCE_MESSAGE);
	        response.setAccountInfo(null);
	        return response;
	    } else {
	        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
	        userRepository.save(userToDebit);
	        
	        // Save Transaction
	        TransactionDto transactionDto = new TransactionDto();
	        transactionDto.setAccountNumber(userToDebit.getAccountNumber());
	        transactionDto.setTransactionType("DEBIT");
	        transactionDto.setAmount(request.getAmount());
	        transactionService.saveTransaction(transactionDto);
	        
	        // Create AccountInfo object
	        AccountInfo accountInfo = new AccountInfo();
	        accountInfo.setAccountNumber(request.getAccountNumber());
	        accountInfo.setAccountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName());
	        accountInfo.setAccountBalance(userToDebit.getAccountBalance());
	        
	        // Create and return BankResponse object
	        BankResponse response = new BankResponse();
	        response.setResponseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS);
	        response.setResponseMessage(AccountUtils.ACCOUNT_DEBITED_SUCCESS_MESSAGE);
	        response.setAccountInfo(accountInfo);
	        
	        return response;
	    }
	}

	@Override
	public BankResponse transfer(TransferRequest request) {
	    boolean isDestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
	    if (!isDestinationAccountExist) {
	        BankResponse response = new BankResponse();
	        response.setResponseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE);
	        response.setResponseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE);
	        response.setAccountInfo(null);
	        return response;
	    }

	    User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
	    if (request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
	        BankResponse response = new BankResponse();
	        response.setResponseCode(AccountUtils.INSUFFICICENT_BALANCE_CODE);
	        response.setResponseMessage(AccountUtils.INSUFFICICENT_BALANCE_MESSAGE);
	        response.setAccountInfo(null);
	        return response;
	    }

	    sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
	    String sourceUsername = sourceAccountUser.getFirstName() + sourceAccountUser.getLastName() + sourceAccountUser.getOtherName();
	    userRepository.save(sourceAccountUser);

	    EmailDetails debitAlert = new EmailDetails();
	    debitAlert.setSubject("DEBIT ALERT");
	    debitAlert.setRecipient(sourceAccountUser.getEmail());
	    debitAlert.setMessageBody("The Sum of " + request.getAmount() + " has been debited from your bank account! Your current balance is "
	            + sourceAccountUser.getAccountBalance());
	    emailService.sendEmailAlert(debitAlert);

	    User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
	    destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
	    userRepository.save(destinationAccountUser);

	    EmailDetails creditAlert = new EmailDetails();
	    creditAlert.setSubject("CREDIT ALERT");
	    creditAlert.setRecipient(destinationAccountUser.getEmail());
	    creditAlert.setMessageBody("The Sum of " + request.getAmount() + " has been sent to your bank account from " + sourceUsername +
	            ". Your current balance is " + destinationAccountUser.getAccountBalance());
	    emailService.sendEmailAlert(creditAlert);

	    TransactionDto transactionDto = new TransactionDto();
	    transactionDto.setAccountNumber(destinationAccountUser.getAccountNumber());
	    transactionDto.setTransactionType("CREDIT");
	    transactionDto.setAmount(request.getAmount());
	    transactionService.saveTransaction(transactionDto);

	    BankResponse response = new BankResponse();
	    response.setResponseCode(AccountUtils.TRANSFER_SUCCESSFULLY_CODE);
	    response.setResponseMessage(AccountUtils.TRANSFER_SUCCESSFULLY_MESSAGE);
	    response.setAccountInfo(null);

	    return response;
	}


}



