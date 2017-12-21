package com.github.account.core.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.account.core.entity.Account;

public interface AccountDAO extends PagingAndSortingRepository<Account, Long> {
	

}
