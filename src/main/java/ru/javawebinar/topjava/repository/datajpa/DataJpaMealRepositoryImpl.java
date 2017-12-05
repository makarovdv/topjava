package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_DATE_DIME = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(),userId)==null){
            return null;
        }
        meal.setUser(crudUserRepository.getOne(userId));
        return crudMealRepository.save(meal);
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudMealRepository.getOneByIdAndUserId(id,userId);
    }

    @Override
    public Meal getMealByIdWithUser(int id, int userId){
        return crudMealRepository.getMealByIdWithUser(id,userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserId(userId,SORT_DATE_DIME);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.getAllBetween(startDate,endDate,userId);
    }
}
