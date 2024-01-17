package com.springbooot.tutorials.springmongodbdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
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
    @Field(name = "transactionDate")
    private Date createdAt;

    @Field(name = "user")
    @JsonIgnore
    private String user;
    @Field(name = "attributes")
    @TextIndexed(weight = 4)
    private List<ExpenseAtttibute> attributes;

    public Expense() {
    }

    public Expense(String id, String expenseName, ExpenseCategory expenseCategory, BigDecimal expenseAmount, Date createdAt, String user, List<ExpenseAtttibute> attributes) {
        this.id = id;
        this.expenseName = expenseName;
        this.expenseCategory = expenseCategory;
        this.expenseAmount = expenseAmount;
        this.createdAt = createdAt;
        this.user = user;
        this.attributes = attributes;
    }

    public Expense(String expenseName, ExpenseCategory expenseCategory, BigDecimal expenseAmount) {
        //this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", expenseName='" + expenseName + '\'' +
                ", expenseCategory=" + expenseCategory +
                ", expenseAmount=" + expenseAmount +
                ", createdAt=" + createdAt +
                ", user='" + user + '\'' +
                ", attributes=" + attributes +
                '}';
    }
}