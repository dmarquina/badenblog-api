package com.badenblog.security.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRestController {
	
    @Value("${jwt.header}")
    private String tokenHeader;






















}


