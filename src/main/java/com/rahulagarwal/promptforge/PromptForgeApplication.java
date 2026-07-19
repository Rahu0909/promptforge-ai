package com.rahulagarwal.promptforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class PromptForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromptForgeApplication.class, args);
    }

}
