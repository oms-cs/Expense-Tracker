package com.springbooot.tutorials.springmongodbdemo.repository;

import com.springbooot.tutorials.springmongodbdemo.model.Expense;
import com.springbooot.tutorials.springmongodbdemo.model.ExpenseAtttibute;
import com.springbooot.tutorials.springmongodbdemo.model.ExpenseCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@ExtendWith(SpringExtension.class)
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository expenseRepository;


    @Test
    void expense_save_should_return_given_expense(){
        //Given - An Instance Object of Expense
        Expense expense = new Expense("101"
                                        ,"Movie Ticket"
                                        , ExpenseCategory.ENTERTAINMENT
                                        , new BigDecimal(199), LocalDateTime.now()
                                        ,"oms17"
                                        , Arrays.asList(new ExpenseAtttibute("Movie Name", "Swades")));

        // When - Saved to DB

        var savedExpense = expenseRepository.save(expense);

        // Then - should return saved expense same as given expense

        Assertions.assertEquals(expense, savedExpense);

    }


    @Test
    void should_return_all_saved_expenses_on_fetch_all(){

        //Given - few Expenses Added
        Expense expense1 = new Expense("101"
                                        ,"Movie Ticket"
                                        , ExpenseCategory.ENTERTAINMENT
                                        , new BigDecimal(199), LocalDateTime.now()
                                        ,"oms17"
                                        , Arrays.asList(new ExpenseAtttibute("Movie Name", "Swades")));
        Expense expense2 = new Expense("102"
                                        ,"Movie Ticket"
                                        , ExpenseCategory.ENTERTAINMENT
                                        , new BigDecimal(258), LocalDateTime.now()
                                        ,"oms17"
                                        , Arrays.asList(new ExpenseAtttibute("Movie Name", "IronMan")));
        Expense expense3 = new Expense("103"
                                        ,"Movie Ticket"
                                        , ExpenseCategory.ENTERTAINMENT
                                        , new BigDecimal(899), LocalDateTime.now()
                                        ,"oms17"
                                        , Arrays.asList(new ExpenseAtttibute("Movie Name", "Oppenheimer 4KIMAX")));
        expenseRepository.save(expense1);
        expenseRepository.save(expense2);
        expenseRepository.save(expense3);

        // When - Fetched List of All Expenses
        List<Expense> expenseList = expenseRepository.findAll();

        // Then  - should Return All Saved Expenses
        Assertions.assertEquals(3,expenseList.size());

    }

}