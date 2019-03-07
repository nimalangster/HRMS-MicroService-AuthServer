package com.microservice.authserver;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/rest/user")
public class UserResource {


    @GetMapping("/principal")
    public Authentication user(Authentication authentication) {
        return authentication;
    }
    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
