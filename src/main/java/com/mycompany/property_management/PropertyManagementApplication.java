package com.mycompany.property_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Enables springbootconfiguration, EnableAutoConfiguration and componentscan
@SpringBootApplication
public class PropertyManagementApplication {

	public static void main(String[] args) {

		// Starts and embedded TOMCAT server for us.
		SpringApplication.run(PropertyManagementApplication.class, args);
	}

}
