package com.boxy.platform.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommonQueryRepository {
    private EntityManagerFactory entityManagerFactory;

    public CommonQueryRepository(
        EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<?> findAll(String hql, Object... params) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<?> result = null;
        try {
            Query query = entityManager.createQuery(hql);
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
            result = query.getResultList();
        } finally {
            entityManager.close();
        }

        return result;
    }

    public <T> List<T> findAll(String hql, Class clazz, Object... params) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<T> result = null;
        try {
            Query query = entityManager.createNativeQuery(hql, clazz);
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
            result = query.getResultList();
        } finally {
            entityManager.close();
        }

        return result;
    }

    public Long findCount(String hql, Object... params) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Long result = null;
        try {
            Query query = entityManager.createQuery(hql);
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
            result = (Long)query.getSingleResult();
        } finally {
            entityManager.close();
        }

        return result;
    }
}


