package com.springbooot.tutorials.springmongodbdemo.controller;

import com.springbooot.tutorials.springmongodbdemo.model.Expense;
import com.springbooot.tutorials.springmongodbdemo.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    public  ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @PostMapping("/add")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity addExpense(@RequestBody Expense expense, Principal principal){
        expense.setUser(principal.getName());
        expenseService.addExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense "+expense.getExpenseName() + " Added Successfully!");
    }

    @GetMapping("/private")
    public String privatePage(){
        return "This is Private Page";
    }

    @PutMapping("/update-expense")
    public ResponseEntity updateExpense(@RequestBody Expense expense, Principal principal){
        expense.setUser(principal.getName());
        expenseService.updateExpense(expense);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all-expenses")
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> getAllExpenses(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                        Principal principal){
        List<Expense> expenses = expenseService.getAllExpenses(page, limit);

        return expenses.stream().filter(expense -> expense.getUser().equalsIgnoreCase(principal.getName())).toList();
    }
    @GetMapping("/get-expense/{expenseName}")
    @ResponseStatus(HttpStatus.OK)
    public Expense getExpenseByName(@PathVariable String expenseName,Principal principal){
        return expenseService.getExpenseByName(expenseName, principal.getName());
    }

    @DeleteMapping("/delete-expense")
    public ResponseEntity deleteExpense(@RequestBody String expenseId){
        expenseService.deleteExpense(expenseId);
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
