package com.example.demo.repository;

import java.util.List;

/* Generisk interface der beskriver generiske CRUD operationer
     Dette interface extendes af et mere specifikt interface for hvert repository.
     Det giver mulighed for mere specialiserede metoder for hvert repository,
     men sørger for at de stadig har de samme basale funktioner,
     og mulighed for nemmere at udskifte implementation hvis det skulle være nødvendigt.
 */
public interface ICRUDRepository<T> {
     void create(T t);
     T read(int id);
     void update(T t);
     void delete(int id);
     List<T> readAll();
}