package com.example.demo.Mapper;

import com.example.demo.Bean.Car;
import com.example.demo.Bean.MaintenanceInfo;
import com.example.demo.Dao.MaintenanceDao;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceInfoMapper extends BaseMapper<MaintenanceInfo> {
    private static MaintenanceInfoMapper mapper;

    private final MaintenanceDao dao;

    public MaintenanceInfoMapper(MaintenanceDao dao) {
        this.dao = dao;
    }

    public static MaintenanceInfoMapper getInstance(MaintenanceDao dao) {
        if (mapper == null) {
            mapper = new MaintenanceInfoMapper(dao);
        }
        return mapper;
    }

    public MaintenanceInfo getSingleInfo(int mid) {
        return dao.findById(mid).get();
    }

    public List<MaintenanceInfo> getInfosByUID(int uid) {
        return dao.getMaintenanceInfoByUid(uid);
    }

    public boolean isBelongUser(int uid, int mid, int type) {
        List<MaintenanceInfo> infos = getInfosByUID(uid);
        boolean ans = type == 1;
        for (MaintenanceInfo i : infos) {
            if (i.getMid() == mid) {
                ans = true;
                break;
            }
        }
        return ans;
    }


    @Override
    public void saveEntity(MaintenanceInfo info) {
        dao.save(info);
    }

    @Override
    public void deleteEntity(MaintenanceInfo info) {
        dao.delete(info);
    }

    @Override
    public Page<MaintenanceInfo> searchAllEntities(MaintenanceInfo info, int page, int size) {
        return null;
    }


    public Page<MaintenanceInfo> searchAllMaintenances(MaintenanceInfo info, int page, int size, int type, int uid) {
        PageRequest pageable = PageRequest.of(page, size);
        return dao.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (type == 0) {
                Join<Car, MaintenanceInfo> join = root.join("car", JoinType.LEFT);
                list.add(criteriaBuilder.equal(join.get("uid"), uid));
            }

            if (info.getCid() != 0) {
                list.add(criteriaBuilder.equal(root.get("cid"), info.getCid()));
            }
            if (!info.getPosition().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("position"), '%' + info.getPosition() + '%'));
            }
            if (!info.getQuestion().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("question"), '%' + info.getQuestion() + '%'));
            }
            if (!info.getDate().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("date"), '%' + info.getDate() + '%'));
            }
            if (!info.isIssolve()) {
                list.add(criteriaBuilder.equal(root.get("issolve"), false));
            } else {
                list.add(criteriaBuilder.equal(root.get("issolve"), true));
            }
            if (info.getValue() != 0) {
                list.add(criteriaBuilder.equal(root.get("value"), info.getValue()));
            }

            Predicate[] predicates = new Predicate[list.size()];
            return query.where(list.toArray(predicates)).getRestriction();
        }, pageable);

    }
}
