package com.skillforge.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skillforge.entity.Transaction;

public interface TransactionDao extends JpaRepository<Transaction, Long> {

}
