package com.example.demo.Dao;

import com.example.demo.Bean.MaintenanceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MaintenanceDao extends JpaRepository<MaintenanceInfo, Integer> {

    @Query(value = "select (MaintenanceInfo.cid), mid, date , position, question, issolve,(MaintenanceInfo.value) from MaintenanceInfo left join Car on MaintenanceInfo.cid = Car.cid where Car.uid = ?1", nativeQuery = true)
    List<MaintenanceInfo> getMaintenanceInfoByUid(int uid);

    @Query(value = "select (MaintenanceInfo.cid), mid, date , position, question, issolve,(MaintenanceInfo.value) from MaintenanceInfo left join Car on MaintenanceInfo.cid = Car.cid",nativeQuery = true)
    Page<MaintenanceInfo> findAllBySelect(Specification specification, Pageable pageable, int uid);

    Page<MaintenanceInfo> findAll(Specification specification, Pageable pageable);
}
