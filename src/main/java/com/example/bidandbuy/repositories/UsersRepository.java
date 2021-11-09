package com.example.bidandbuy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bidandbuy.models.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	Users findByUsername(String username);
}
