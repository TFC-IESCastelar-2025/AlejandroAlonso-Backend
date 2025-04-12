package com.daw2.fct_bbdd;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FctBbddApplication {

    Dotenv dotenv = Dotenv.load();

    public static void main(String[] args) {
        SpringApplication.run(FctBbddApplication.class, args);
    }

}
