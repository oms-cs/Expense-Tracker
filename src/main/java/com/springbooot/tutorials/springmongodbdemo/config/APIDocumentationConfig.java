package com.springbooot.tutorials.springmongodbdemo.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIDocumentationConfig {

    public OpenAPI configureAPIDoc(){
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Management")
                        .description("Manage your Expenses using API's")
                        .version("0.0.1")
                        );
    }
}
