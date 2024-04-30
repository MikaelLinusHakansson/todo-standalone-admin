package com.example.todoappstandaloneadmin.repository;

import com.example.todoappstandaloneadmin.entity.TodoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SqlTodoRepository implements TodoRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TodoEntity> findByUserId(int userId) {
        return entityManager.createQuery("SELECT t FROM TodoEntity t WHERE t.user.id = :userId", TodoEntity.class).setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional
    public <S extends TodoEntity> S save(S entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }

        return entity;
    }

    @Override
    @Transactional
    public void deleteById(Long aLong) {
        TodoEntity todo = entityManager.find(TodoEntity.class, aLong);
        entityManager.remove(todo);
    }

    @Override
    @Transactional
    public void delete(TodoEntity entity) {
        TodoEntity todo = entityManager.find(TodoEntity.class, entity.getId());
        entityManager.remove(todo);
    }

    @Override
    @Transactional
    public <S extends TodoEntity> Iterable<S> saveAll(Iterable<S> entities) {
        for (S entity : entities) {
            entityManager.persist(entity);
        }

        return entities;
    }

    @Override
    public Optional<TodoEntity> findById(Long aLong) {
        TodoEntity todo = entityManager.find(TodoEntity.class, aLong);
        return Optional.ofNullable(todo);
    }

    @Override
    public boolean existsById(Long aLong) {
        return entityManager.find(TodoEntity.class, aLong) != null;
    }

    @Override
    public Iterable<TodoEntity> findAll() {
        return entityManager.createQuery("SELECT t FROM TodoEntity t", TodoEntity.class)
                .getResultList();
    }

    @Override
    public Iterable<TodoEntity> findAllById(Iterable<Long> longs) {
        return entityManager.createQuery("SELECT t FROM TodoEntity t WHERE t.id IN :longs", TodoEntity.class)
                .setParameter("longs", longs)
                .getResultList();
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(t) FROM TodoEntity t", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public void deleteAllById(Iterable<? extends Long> longs) {
        entityManager.createQuery("DELETE FROM TodoEntity t WHERE t.id IN :longs").setParameter("longs", longs).executeUpdate();
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends TodoEntity> entities) {
        for (TodoEntity entity : entities) {
            entityManager.remove(entity);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM TodoEntity t").executeUpdate();
    }
}
