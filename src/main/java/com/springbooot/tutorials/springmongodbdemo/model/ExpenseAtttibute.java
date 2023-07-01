package com.springbooot.tutorials.springmongodbdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class ExpenseAtttibute {

    private String attribute_name;

    private String attribute_value;
}
