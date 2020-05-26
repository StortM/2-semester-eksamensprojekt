package com.example.demo.repository;

import java.util.List;

public interface ICRUDRepository<T> {
     void create(T t);
     T read(int id);
     void update(T t);
     void delete(int id);
     List<T> readAll();
}