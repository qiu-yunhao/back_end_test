package com.example.demo.Dao;

import com.example.demo.Bean.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Integer> {

    @Query(value = "select * from Image where path = ?1", nativeQuery = true)
    List<Image> getImageByPath(String path);

    @Query(value = "select * from Image where pid = ?1", nativeQuery = true)
    List<Image> getImageByID(int pid);

}
