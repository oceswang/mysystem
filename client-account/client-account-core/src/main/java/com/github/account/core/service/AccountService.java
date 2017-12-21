package com.github.account.core.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.account.core.entity.Account;

@Service
public class AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private AccountService service;
	@Transactional
	public Account init(Long userId)
	{
		logger.debug(String.format("Init Account for user %s", userId));
		Account account = new Account();
		account.setBalance(BigDecimal.ZERO);
		account.setUserId(userId);
		account.setCreatedTime(LocalDateTime.now());
		return account;
	}
	@Transactional
	public Account save(Account account)
	{
		return service.save(account);
	}
}
