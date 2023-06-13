package com.huncho.tripubank.customer.repository;

import com.huncho.tripubank.customer.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Boolean existsUserByEmail(String email);
    Optional <User> findUserByAccountNumber(String accountNumber);

    Optional <User> findUserByEmail(String email);
}
