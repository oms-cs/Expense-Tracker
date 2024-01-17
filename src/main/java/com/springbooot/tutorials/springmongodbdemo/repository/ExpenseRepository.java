package com.springbooot.tutorials.springmongodbdemo.repository;

import com.springbooot.tutorials.springmongodbdemo.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ExpenseRepository extends MongoRepository<Expense, String> {

    public Expense findByExpenseName(String expenseName);

    public Page<Expense> findByUser(String user, Pageable pageable);
}
