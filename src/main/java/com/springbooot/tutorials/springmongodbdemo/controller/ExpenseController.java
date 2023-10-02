package com.springbooot.tutorials.springmongodbdemo.controller;

import com.springbooot.tutorials.springmongodbdemo.model.Expense;
import com.springbooot.tutorials.springmongodbdemo.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    public  ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "*")
    public ResponseEntity addExpense(@RequestBody Expense expense){
        expenseService.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update-expense")
    public ResponseEntity updateExpense(@RequestBody Expense expense){
        expenseService.updateExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all-expenses")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getAllExpenses(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit){
        return expenseService.getAllExpenses(page, limit);
    }
    @GetMapping("/get-expense/{expenseName}")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseByName(@PathVariable String expenseName){
        return expenseService.getExpenseByName(expenseName);
    }

    @DeleteMapping("/delete-expense")
    public ResponseEntity deleteExpense(@RequestBody Expense expense){
        expenseService.deleteExpense(expense);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> searchInExpense(@RequestParam String expenseName){
        return expenseService.fullTextSearch(expenseName);
    }

}
