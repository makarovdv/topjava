package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        User user = em.getReference(User.class,userId);
        meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return isOwner(meal,userId) ? em.merge(meal) : null;
        }
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
                .setParameter(1, id)
                .setParameter(2, userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class,id);
        return isOwner(meal,userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("1", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED_BETWEEN, Meal.class)
                .setParameter("1", userId)
                .setParameter("2", startDate)
                .setParameter("3", endDate)
                .getResultList();
    }

    private boolean isOwner(Meal meal, int userId){
        User owner = em.getReference(Meal.class,meal.getId()).getUser();
        return owner.getId() == userId;
    }
}