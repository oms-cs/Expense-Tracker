package com.springbooot.tutorials.springmongodbdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.List;

@Document(value = "expense")
public class Expense {

    @Id
    private String id;
    @Field(name = "name")
    @TextIndexed(weight = 5)
    private String expenseName;
    @Field(name = "category")
    private ExpenseCategory expenseCategory;
    @Field(name = "amount")
    private BigDecimal expenseAmount;

    @Field(name = "attributes")
    @TextIndexed(weight = 4)
    private List<ExpenseAtttibute> attributes;

    public Expense() {
    }

    public Expense(String id, String expenseName, ExpenseCategory expenseCategory, BigDecimal expenseAmount, List<ExpenseAtttibute> attributes) {
        this.id = id;
        this.expenseName = expenseName;
        this.expenseCategory = expenseCategory;
        this.expenseAmount = expenseAmount;
        this.attributes = attributes;
    }

    public Expense(String id, String expenseName, ExpenseCategory expenseCategory, BigDecimal expenseAmount) {
        this.id = id;
        this.expenseName = expenseName;
        this.expenseCategory = expenseCategory;
        this.expenseAmount = expenseAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public List<ExpenseAtttibute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ExpenseAtttibute> attributes) {
        this.attributes = attributes;
    }
}