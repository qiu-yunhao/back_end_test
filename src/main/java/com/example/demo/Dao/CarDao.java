package com.example.demo.Dao;

import com.example.demo.Bean.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarDao extends JpaRepository<Car, Integer> {

    @Query(value = "select * from Car where uid = ?1", nativeQuery = true)
    List<Car> findCarByUid(int uid);

    @Query(value = "select * from Car where cid = ?1", nativeQuery = true)
    Car findCarByCid(int cid);

    Page<Car> findAll(Specification specification, Pageable pageable);
}
