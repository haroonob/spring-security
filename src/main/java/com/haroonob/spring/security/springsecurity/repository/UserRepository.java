package com.haroonob.spring.security.springsecurity.repository;
import com.haroonob.spring.security.springsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String>{

}