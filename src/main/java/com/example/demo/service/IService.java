package com.example.demo.service;

import java.util.List;

/* Generisk interface der beskriver generiske CRUD operationer
     Dette interface extendes af et mere specifikt interface for hver service.
     Det giver mulighed for mere specialiserede metoder for hver service,
     men sørger for at de stadig har de samme basale funktioner,
     og mulighed for nemmere at udskifte implementation hvis det skulle være nødvendigt.

     Service metodenavnene adskiller sig fra navnene i ICrudRepository,
     selvom add i servicelaget kalder create i repository laget osv.

     Det skyldes at det virkede mere brugervenligt på denne måde, da det
     efterligner metodenavne fra f.eks. List interfacet.
 */
public interface IService<T> {
    void add(T t);
    T get(int id);
    void update(T t);
    void remove(int id);
    List<T> getAll();
}
