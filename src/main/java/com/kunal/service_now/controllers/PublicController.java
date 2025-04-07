package com.kunal.service_now.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    // greeting endpoint
    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello from a public endpoint! You don't need to be authenticated to see this.";
    }

}
