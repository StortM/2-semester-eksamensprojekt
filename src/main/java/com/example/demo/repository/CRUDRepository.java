package com.example.demo.repository;

import java.util.Collection;

public interface CRUDRepository<T> {
     void create(T t);
     T read(int id);
     void update(T t);
     void delete(int id);
     Collection<T> readAll();
}