package com.sunkuet02.micro.authentication.dao;

import com.sunkuet02.micro.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u " +
            "from User u " +
            "where u.username = :username")
    User findByUsername(@Param("username") String username);
}
