package com.example.demo.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class BaseMapper<T> {
    public Page<T> toPage(List<T> list, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    public abstract void saveEntity(T t);

    public abstract void deleteEntity(T t);

    public abstract Page<T> searchAllEntities(T t, int page, int size);
}
