package com.example.demo.Dao;

import com.example.demo.Bean.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {

    @Query(value = "select * from UserInfo where uid = ?1",nativeQuery = true)
    UserInfo getUserInfoByUID(int uid);

    @Query(value = "select UserInfo.id, name, email, phone, address,uid,pid  from UserInfo left join Users on UserInfo.uid = Users.id where username = ?1",nativeQuery = true)
    UserInfo getUserInfoByUName(String name);

    @Query(value = "select * from UserInfo where id = ?1",nativeQuery = true)
    UserInfo getUserInfoByID(int id);


    Page<UserInfo> findAll(Specification<UserInfo> specification, Pageable pageable);
}
