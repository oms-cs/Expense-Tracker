package com.springbooot.tutorials.springmongodbdemo.service;

import com.springbooot.tutorials.springmongodbdemo.exception.DuplicateDataException;
import com.springbooot.tutorials.springmongodbdemo.exception.NotFoundException;
import com.springbooot.tutorials.springmongodbdemo.model.Expense;
import com.springbooot.tutorials.springmongodbdemo.repository.ExpenseRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class ExpenseService {

    private MongoTemplate mongoTemplate; // inject mongo template via constructor

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository, MongoTemplate mongoTemplate){
        this.expenseRepository = expenseRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * <p>
     *     For Saving Expense based on user.
     *     returns http response entity.
     * </p>
     * @param expense
     * @return expense
     * @author om shinde
     * */
    public Expense addExpense(Expense expense){
        log.info("expense == "+expense);
        var existingExpense = expenseRepository.findByExpenseName(expense.getExpenseName());
        log.info("ExistingExpense == "+existingExpense);
        Expense addedExpense = null;
        if(!(existingExpense == null)) {
            log.error("Expense With Id : {} Already Exists!! Try with Another Id.",expense.getId());
            throw new DuplicateDataException("Expense With Id : "+expense.getId() + "Already Exists!! Try with Another Id.");
        }
        expense.setCreatedAt(LocalDateTime.now());
        addedExpense = expenseRepository.save(expense);
        log.info("addedExpense == "+addedExpense);
        return addedExpense;
    }

    /**
     * <p>
     *     For Updating Expense based on user.
     *     returns http response entity.
     * </p>
     * @param expense
     * @author om shinde
     * */
    public Expense updateExpense(Expense expense, String id){
        Expense savedExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Unable to Find Expense with Id :"+expense.getId()));
        if (!savedExpense.getUser().equalsIgnoreCase(expense.getUser())){
            log.error("{} no such expense exists for User {}",expense.getId(),expense.getUser());
            throw new NotFoundException("Unable to Find Expense with Id :" + expense.getId() + "for User "+expense.getUser());
        }
        savedExpense.setExpenseName(expense.getExpenseName());
        savedExpense.setExpenseAmount(expense.getExpenseAmount());
        savedExpense.setExpenseCategory(expense.getExpenseCategory());
        return expenseRepository.save(savedExpense);
    }

    /**
     * <p>
     *     this method fetch expenses by user based on
     * </p>
     * @param page
     * @param limit
     * @return responseEntity
     * @author om shinde
     * */
    public List<Expense> getAllExpenses(int page, int limit, String username){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Expense> expensesPage =  expenseRepository.findByUser(username,pageable);
        if(expensesPage.isEmpty()) {
            log.error("No Records Found !!");
            throw new NotFoundException("No Records Found !!");
        }
        List<Expense> expenseList =  expensesPage.getContent();
        log.info("all-expenses-for-user {} are {}",username,expenseList);
        return expenseList;
                //.stream().filter(expense -> expense.getUser().equalsIgnoreCase(username)).toList();
    }
    public Expense getExpenseByName(String expenseName, String user){
        Expense expense =  expenseRepository.findByExpenseName(expenseName);
        if(expense ==  null){
            log.error("Expense {} not Found!!",expenseName);
            throw new NotFoundException("Expense "+expenseName+" Not Found!!");
        }
        if(!expense.getUser().equalsIgnoreCase(user)){
            log.error("{} No Such Expense Found for User {}",expenseName, user);
            throw new NotFoundException("Expense "+expenseName+" Not Found!! for User "+user);
        }
        return expense;
    }
    public void deleteExpense(String expenseId){
        log.info("deleteExpense service == "+expenseId);
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
