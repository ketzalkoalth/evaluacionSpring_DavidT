package com.evaluacion.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = DataSourceAutoConfiguration.class)
public class EvaluacionspringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvaluacionspringApplication.class, args);
	}

}
