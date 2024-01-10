package com.example.demo.Mapper;

import com.example.demo.Bean.Users;
import com.example.demo.Dao.UsersDao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class UserMapper extends BaseMapper<Users> {
    private static UserMapper mapper = null;

    private final UsersDao dao;

    UserMapper(UsersDao dao) {
        this.dao = dao;
    }

    public static UserMapper getInstance(UsersDao dao) {
        if (mapper == null) {
            mapper = new UserMapper(dao);
        }
        return mapper;
    }

    public Users getUserByName(String name) {
        List<Users> list = dao.getUsersByName(name);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<Users> getAllUsers() {
        return dao.findAll();
    }

    public Users getUserById(int id) {
        List<Users> list = dao.getUsersByID(id);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }


    @Override
    public void saveEntity(Users users) {
        dao.save(users);
    }

    @Override
    public void deleteEntity(Users users) {
        dao.delete(users);
    }

    @Override
    public Page<Users> searchAllEntities(Users u, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return dao.findAll(((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!u.getUsername().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("username"), '%' + u.getUsername() + '%'));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return query.where(list.toArray(predicates)).getRestriction();
        }), pageRequest);
    }
}
