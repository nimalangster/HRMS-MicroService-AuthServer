package com.microservice.authserver.restcontroller;



import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/hello")
public class Hello {


    @GetMapping("/principal")
    public Principal user(Principal principal) {
        return principal;
    }
    @GetMapping
    public String hello() {
        return "Hello World";
    }
}