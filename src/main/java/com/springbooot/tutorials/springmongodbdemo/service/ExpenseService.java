package com.springbooot.tutorials.springmongodbdemo.service;

import com.springbooot.tutorials.springmongodbdemo.exception.DuplicateDataException;
import com.springbooot.tutorials.springmongodbdemo.exception.NotFoundException;
import com.springbooot.tutorials.springmongodbdemo.model.Expense;
import com.springbooot.tutorials.springmongodbdemo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository, MongoTemplate mongoTemplate){
        this.expenseRepository = expenseRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Expense addExpense(Expense expense){
        var existingExpense = expenseRepository.findById(expense.getId());
        Expense addedExpense = null;
        if(!existingExpense.isEmpty()) {
            throw new DuplicateDataException("Expense With Id : "+expense.getId() + "Already Exists!! Try with Another Id.");
        }
        addedExpense = expenseRepository.insert(expense);
        return addedExpense;
    }
    public void updateExpense(Expense expense){
        Expense savedExpense = expenseRepository.findById(expense.getId())
                .orElseThrow(() -> new NotFoundException("Unable to Find Expense with Id :"+expense.getId()));
        savedExpense.setExpenseName(expense.getExpenseName());
        savedExpense.setExpenseAmount(expense.getExpenseAmount());
        savedExpense.setExpenseCategory(expense.getExpenseCategory());
        expenseRepository.save(savedExpense);
    }
    public List<Expense> getAllExpenses(int page, int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Expense> expensesPage =  expenseRepository.findAll(pageable);
        if(expensesPage.isEmpty() || expensesPage == null) throw new NotFoundException("No Records Found !!");
        return expensesPage.getContent();
    }
    public Expense getExpenseByName(String expenseName, String user){
        Expense expense =  expenseRepository.findByExpenseName(expenseName);
        if(expense ==  null){
            throw new NotFoundException("Expense "+expenseName+" Not Found!!");
        }
        if(!expense.getUser().equalsIgnoreCase(user)){
            throw new NotFoundException("Expense "+expenseName+" Not Found!!");
        }
        return expense;
    }
    public void deleteExpense(String expenseId){
         expenseRepository.deleteById(expenseId);
    }

    public void deleteAllExpense(){
        expenseRepository.deleteAll();
    }

    public List<Expense> fullTextSearch(String searchPhrase) {

        mongoTemplate.indexOps(Expense.class)
                .ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder().onFields( "name","attributes.attribute_value").build());

        TextCriteria textCriteria = TextCriteria
                .forDefaultLanguage()
                .caseSensitive(false)
                .matchingPhrase(searchPhrase);

        Query query = TextQuery.queryText(textCriteria)
                        .sortByScore();


        return mongoTemplate.find(query, Expense.class);
    }
}
