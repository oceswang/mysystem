package com.galaxyinternet.account.core.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.galaxyinternet.account.core.entity.Account;

public interface AccountDAO extends PagingAndSortingRepository<Account, Long> {
	

}
