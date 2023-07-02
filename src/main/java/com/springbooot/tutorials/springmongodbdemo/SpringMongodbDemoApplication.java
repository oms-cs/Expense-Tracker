package com.springbooot.tutorials.springmongodbdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class SpringMongodbDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbDemoApplication.class, args);
	}


}
