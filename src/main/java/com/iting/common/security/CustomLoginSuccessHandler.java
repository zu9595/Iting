package com.iting.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		log.info("Login Success");
		List<String> roleNames = new ArrayList<>();
		//Collection<Authority> ==> List<String>
		auth.getAuthorities().forEach(authority -> {roleNames.add(authority.getAuthority());});
		
		if(roleNames.contains("ROLE_B2")) {
			response.sendRedirect("/admin/main");
		} else {
			response.sendRedirect("/member/main");
		}
	}

}
