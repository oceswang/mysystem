package com.github.user.core.dao.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.user.core.entity.User;

public interface UserDAO extends JpaRepository<User, Long>
{

}
