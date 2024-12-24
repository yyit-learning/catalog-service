package com.polarbookshop.catalogservice.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {

    @GetMapping("/")
    public String getGreetint() {
        return "Welcome to the book catalog!";
    }
        
    
}
