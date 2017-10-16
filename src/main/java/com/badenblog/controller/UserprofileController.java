package com.badenblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.badenblog.common.RestDataMessageResponse;
import com.badenblog.persistence.domain.Userprofile;
import com.badenblog.security.jwt.JwtTokenUtil;
import com.badenblog.security.jwt.JwtUser;
import com.badenblog.service.UserprofileService;

@RestController
@RequestMapping(value = "/user")
public class UserprofileController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
	private UserprofileService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public RestDataMessageResponse create(@RequestBody Userprofile user) {
		RestDataMessageResponse response;
		response = userService.create(user);
		return response;
	}
    
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Userprofile findById(@PathVariable int id) {
		return userService.findOne(id);
	}
	
    @RequestMapping(value = "/jwtUser", method = RequestMethod.POST)
	public Userprofile authenticatedUser(@RequestBody String tokenFront) {
        String username = jwtTokenUtil.getUsernameFromToken(tokenFront);
        try {
        	JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
            return userService.findOne(user.getId().intValue());
		} catch (Exception e) {
			System.out.println("Error en /jwtUsuario: "+e.getMessage());
			return null;
		}
    }
}
