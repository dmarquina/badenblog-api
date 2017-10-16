package com.badenblog.security.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.badenblog.persistence.domain.Authority;
import com.badenblog.persistence.domain.Userprofile;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Userprofile user) {
    	boolean stateUser = false;
    	if(user.getActive() == 1) { //Cambiar con variable de Util
    		stateUser = true;
    	}
        return new JwtUser(
                Long.parseLong(String.valueOf(user.getIdUser())),
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                stateUser,
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}
