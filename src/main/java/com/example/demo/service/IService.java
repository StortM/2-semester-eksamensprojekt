package com.example.demo.service;

import java.util.List;

public interface IService<T> {
    void add(T t);
    T get(int id);
    void update(T t);
    void remove(int id);
    List<T> getAll();
}
