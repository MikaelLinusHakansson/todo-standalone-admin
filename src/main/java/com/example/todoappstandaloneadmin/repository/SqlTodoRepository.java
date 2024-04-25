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
        return entityManager.createQuery(
                        "SELECT t FROM TodoEntity t WHERE t.user.id = :userId", TodoEntity.class)
                .setParameter("userId", userId)
                .getResultList();
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
    public <S extends TodoEntity> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TodoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<TodoEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<TodoEntity> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Transactional
    @Override
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
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends TodoEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
