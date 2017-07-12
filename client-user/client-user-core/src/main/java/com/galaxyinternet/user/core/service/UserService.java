package com.galaxyinternet.user.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxyinternet.user.core.dao.jpa.UserDAO;

@Service
public class UserService
{
	@Autowired
	private UserDAO dao;
	
	
}
