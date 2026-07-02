package com.rahulagarwal.promptforge;

import com.rahulagarwal.promptforge.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class PromptForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromptForgeApplication.class, args);
    }

}
