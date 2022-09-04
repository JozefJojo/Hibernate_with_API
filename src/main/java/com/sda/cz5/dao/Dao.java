package com.sda.cz5.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public interface Dao<T> {
    EntityManager getEntityManager();
    default void saveObject(T object) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        if(!transaction.isActive()){
            transaction.begin();
        }
        try {
            getEntityManager().persist(object);
        }catch (Exception e){
            transaction.rollback();
        }
        transaction.commit();

    }
}
