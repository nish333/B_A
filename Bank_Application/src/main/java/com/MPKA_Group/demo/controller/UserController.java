package com.MPKA_Group.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MPKA_Group.demo.dto.BankResponse;
import com.MPKA_Group.demo.dto.CreditDebitRequest;
import com.MPKA_Group.demo.dto.EnquiryRequest;
import com.MPKA_Group.demo.dto.LoginDto;
import com.MPKA_Group.demo.dto.TransferRequest;
import com.MPKA_Group.demo.dto.UserRequest;
import com.MPKA_Group.demo.service.impl.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/bank")
@Tag(name="User Account Management API's")
public class UserController 
{
	
	@Autowired
	private UserService userService;
	
	@Operation
	(
			summary="Create a New Bank Account User",
			description="create a new user and assigned Bank Account Numbber and ID "
	)
	@ApiResponse
	(
			responseCode = "201",
			description="HTTP status 201 Created"
	)
	
	@PostMapping("/add")
	public BankResponse createAccount(@RequestBody UserRequest userRequest) 
	{
		return userService.createAccount(userRequest);	
	}
	
	@PostMapping("/login")
	public BankResponse login(@RequestBody LoginDto loginDto)
	{
		return userService.login(loginDto);
	}
	
	@Operation
	(
			summary="Banalance Enquiry",
			description="Give an account number and check how much anount they have"
	)
	@ApiResponse
	(
			responseCode = "200",
			description="HTTP status 200 Successfully"
	)
	
	@GetMapping("/balance")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request)
	{
		return userService.balanceEnquiry(request);	
	}
	
	
	@GetMapping("/name")
	public String nameEnquiry(@RequestBody EnquiryRequest request)
	{
		return userService.nameEnquiry(request);	
	}
	
	
	@PostMapping("/credit")
	public BankResponse creditAccount(@RequestBody CreditDebitRequest request)
	{
		return userService.creditAccount(request);
	}
	
	
	@PostMapping("/debit")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest request)
	{
		return userService.debitAccount(request);
	}
	
	
	@PostMapping("/transfer")
	public BankResponse transfer(@RequestBody TransferRequest request)
	{
		return userService.transfer(request);
	}

	
}
