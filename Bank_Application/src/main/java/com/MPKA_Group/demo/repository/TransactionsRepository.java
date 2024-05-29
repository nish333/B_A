package com.MPKA_Group.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MPKA_Group.demo.domain.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, String>{

}
