package com.example.demo.Mapper;


import com.example.demo.Bean.UserInfo;
import com.example.demo.Dao.UserInfoDao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserInfoMapper extends BaseMapper<UserInfo> {

    private static UserInfoMapper INSTANCE;

    private final UserInfoDao dao;


    private UserInfoMapper(UserInfoDao dao) {
        this.dao = dao;
    }

    public static UserInfoMapper getInstance(UserInfoDao dao) {
        if (INSTANCE == null) {
            INSTANCE = new UserInfoMapper(dao);
        }
        return INSTANCE;
    }


    public void deleteInfo(UserInfo info) {
        dao.delete(info);
    }

    public UserInfo getUserInfoByUID(int uid) {
        return dao.getUserInfoByUID(uid);
    }

    public UserInfo getUserInfoByUname(String name) {
        return dao.getUserInfoByUName(name);
    }

    public UserInfo getUserInfoByID(int id) {
        return dao.getUserInfoByID(id);
    }

    @Override
    public void saveEntity(UserInfo info) {
        dao.save(info);
    }

    @Override
    public void deleteEntity(UserInfo info) {
        dao.delete(info);
    }

    @Override
    public Page<UserInfo> searchAllEntities(UserInfo info, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return dao.findAll((Specification<UserInfo>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!info.getAddress().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("address"), '%' + info.getAddress() + '%'));
            }
            if (!info.getName().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("name"), '%' + info.getName() + '%'));
            }
            if (!info.getEmail().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("email"), '%' + info.getEmail() + '%'));
            }
            if (!info.getPhone().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("address"), '%' + info.getPhone() + '%'));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return query.where(list.toArray(predicates)).getRestriction();
        }, pageable);
    }
}
