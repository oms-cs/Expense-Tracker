package com.springbooot.tutorials.springmongodbdemo.controller;

import com.springbooot.tutorials.springmongodbdemo.exception.NotFoundException;
import com.springbooot.tutorials.springmongodbdemo.model.Expense;
import com.springbooot.tutorials.springmongodbdemo.service.ExpenseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
@Log4j2
public class ExpenseController {

    private final ExpenseService expenseService; // Inject ExpenseService via ExpenseController Constructor
    public  ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }


    @PostMapping("/add")
    public ResponseEntity addExpense(@RequestBody Expense expense, Principal principal){
        log.info("addExpense Endpoint accessed by User {}",principal.getName());
        expense.setUser(principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.addExpense(expense));
    }

    @GetMapping("/private")
    public String privatePage(){
        log.info("Private Endpoint.");
        return "This is Private Page";
    }


    @PutMapping("/{id}")
    public ResponseEntity updateExpense(@RequestBody Expense expense, @PathVariable String id ,Principal principal){
        log.info("update request "+id);
        expense.setUser(principal.getName());
        return ResponseEntity.ok(expenseService.updateExpense(expense,id));
    }

    @GetMapping("/all-expenses")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getAllExpenses(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        Principal principal){
        log.info("get-all-expenses accessed by == "+principal.getName());
        List<Expense> expenses = expenseService.getAllExpenses(page, limit, principal.getName());
        log.info("expenses for user == "+expenses);
        /*if(expenses.isEmpty()){
            log.error("No Expenses Found for User {}",principal.getName());
            throw new NotFoundException("No Expenses Found!!");
        }
        log.info("all-expenses-listed here == "+expenses.toString());*/
        return expenses;
    }
    @GetMapping("/{expenseName}")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseByName(@PathVariable String expenseName,Principal principal){
        return expenseService.getExpenseByName(expenseName, principal.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteExpense(@PathVariable String id){
        log.info("deleteExpense == "+id);
        expenseService.deleteExpense(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/delete-all-expenses")
    public ResponseEntity deleteAllExpense(){
        expenseService.deleteAllExpense();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> searchInExpense(@RequestParam String expenseName){
        return expenseService.fullTextSearch(expenseName);
    }

}
