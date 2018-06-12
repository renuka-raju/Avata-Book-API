package com.avata.assestment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class AssestmentApplication {

	public static void main(String[] args){
	SpringApplication.run(AssestmentApplication.class, args);
	}
}
