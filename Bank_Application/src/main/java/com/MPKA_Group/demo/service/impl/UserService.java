package com.MPKA_Group.demo.service.impl;


import com.MPKA_Group.demo.dto.BankResponse;
import com.MPKA_Group.demo.dto.CreditDebitRequest;
import com.MPKA_Group.demo.dto.EnquiryRequest;
import com.MPKA_Group.demo.dto.LoginDto;
import com.MPKA_Group.demo.dto.TransferRequest;
import com.MPKA_Group.demo.dto.UserRequest;

public interface UserService 
{
    BankResponse createAccount(UserRequest userRequest);
    
    BankResponse login(LoginDto loginDto);
    
    BankResponse balanceEnquiry(EnquiryRequest request);
    
    String nameEnquiry(EnquiryRequest request);
    
    BankResponse creditAccount(CreditDebitRequest request);
    
    BankResponse debitAccount(CreditDebitRequest request);
    
    BankResponse transfer(TransferRequest request);
    
}
