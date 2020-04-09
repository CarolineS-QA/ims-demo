package com.qa.ims.services;

import java.util.List;

public interface CrudableServices<T> {

    public List<T> readAll();
     
    T create(T t);
     
    T update(T t);
 
    void delete(Long id);

}
