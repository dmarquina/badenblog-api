package com.badenblog.security.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {
	public static String encodePassword(String s) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(s);
		return encodedPassword;
	}
	
	public static boolean matchPassword(String s, String encriptada) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		boolean encodedPassword = passwordEncoder.matches(s, encriptada );
		return encodedPassword;
	}
	
	public static String generarClave(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = length; i > 0; i -= 12) {
			int n = Math.min(12, Math.abs(i));
			sb.append(StringUtils.leftPad(Long.toString(Math.round(Math.random() * Math.pow(36, n)), 36),
					n, '0'));
		}
		return sb.toString();
	}
}
