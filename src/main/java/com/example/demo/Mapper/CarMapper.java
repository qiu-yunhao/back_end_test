package com.example.demo.Mapper;

import com.example.demo.Bean.Car;
import com.example.demo.Bean.MaintenanceInfo;
import com.example.demo.Dao.CarDao;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CarMapper extends BaseMapper<Car> {
    private static CarMapper mapper;

    private final CarDao dao;

    private CarMapper(CarDao carDao) {
        this.dao = carDao;
    }

    public static CarMapper getInstance(CarDao carDao) {
        if (mapper == null) {
            mapper = new CarMapper(carDao);
        }
        return mapper;
    }

    public Car getCarById(int cid) {
        return dao.findCarByCid(cid);
    }

    public List<Car> getCarByUser(int uid) {
        return dao.findCarByUid(uid);
    }

    public Page<Car> getCarByUser(int uid, int page, int size) {
        return toPage(dao.findCarByUid(uid), page, size);
    }

    @Override
    public void saveEntity(Car car) {
        dao.save(car);
    }

    @Override
    public void deleteEntity(Car car) {
        dao.delete(car);
    }

    @Override
    public Page<Car> searchAllEntities(Car car, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return dao.findAll((Specification<MaintenanceInfo>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!car.getCarname().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("carname"), '%' + car.getCarname() + '%'));
            }
            if (!car.getCartype().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("cartype"), '%' + car.getCartype() + '%'));
            }
            if (!car.getBrand().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("brand"), '%' + car.getBrand() + '%'));
            }
            if (!car.getColor().isEmpty()) {
                list.add(criteriaBuilder.like(root.get("color"), '%' + car.getColor() + '%'));
            }
            if (car.getUid() != 0) {
                list.add(criteriaBuilder.equal(root.get("uid"), car.getUid()));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return query.where(list.toArray(predicates)).getRestriction();
        }, pageable);
    }
}
