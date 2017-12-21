package com.github.user.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.user.core.dao.jpa.UserDAO;

@Service
public class UserService
{
	@Autowired
	private UserDAO dao;
	
	
}
