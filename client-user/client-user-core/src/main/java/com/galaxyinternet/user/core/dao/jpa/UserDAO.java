package com.galaxyinternet.user.core.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.galaxyinternet.user.core.entity.User;

public interface UserDAO extends JpaRepository<User, Long>
{

}
