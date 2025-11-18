package com.webshop.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class
        }
)
@ComponentScan({"com.webshop.*"})
public class WebshopWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebshopWebApplication.class, args);
    }

}
