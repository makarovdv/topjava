package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        Meal mealFromDB;
        if (meal.isNew()) {
            User user = new User();
            user.setId(userId);
            meal.setUser(user);
            return crudRepository.save(meal);
        } else if ((mealFromDB = get(meal.getId(),userId)) == null) {
            return null;
        } else {
            meal.setUser(mealFromDB.getUser());
            Meal m = crudRepository.save(meal);
            return m;
        }
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getOneByIdAndUserId(id,userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getBetween(startDate,endDate,userId);
    }
}
