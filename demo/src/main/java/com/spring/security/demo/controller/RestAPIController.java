package com.spring.security.demo.controller;

import com.spring.security.demo.util.JwtTokenUtil;
import com.spring.security.demo.vo.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPIController
{
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping("/api/hi")
	public String hi()
	{
		return "hi";
	}
	
	@RequestMapping("/api/hello")
	public String hello()
	{
		return "hello";
	}
	
	@RequestMapping(value="/api/authenticate2")
	public String hello2(@RequestBody LoginForm form)
	{
		if(form.getUsername().equals("apiuser") && form.getPassword().equals("apiuser@123#")) //or you can use your service to authenticate user
		{
			return jwtTokenUtil.generateToken(form.getUsername());
		}
		else
		{
			return "INVALID CREDENTIAL";
		}
	}

	@RequestMapping(value="/api/authenticate")
	public String hello3()
	{
		return jwtTokenUtil.generateToken("Test");
	}
}
