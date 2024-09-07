package com.willen.OrceJa;

import org.springframework.boot.SpringApplication;

public class TestOrceJaApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrceJaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
