package com.example.demo.Dao;

import com.example.demo.Bean.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersDao extends JpaRepository<Users, Integer> {

    @Query(value = " select * from Users where username = ?1" , nativeQuery = true)
    List<Users> getUsersByName(String name);

    @Query(value = "select * from Users where id = ?1", nativeQuery = true)
    List<Users> getUsersByID(int id);

    Page<Users> findAll(Specification specification, Pageable pageable);

}
