package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.get(0).setUserId(1);
        MealsUtil.MEALS.get(1).setUserId(2);
        MealsUtil.MEALS.get(2).setUserId(1);
        MealsUtil.MEALS.get(3).setUserId(1);
        MealsUtil.MEALS.get(4).setUserId(1);
        MealsUtil.MEALS.get(5).setUserId(1);
        MealsUtil.MEALS.forEach(m -> save(m,m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            log.info("save new meal " + meal);
            meal.setId(counter.incrementAndGet());
        } else {
            log.info("update meal " + meal);
        }
        log.info("save meal " + meal);
        if(!isOwner(repository.get(meal.getId()),meal.getUserId()))
            return null;
        return repository.put(meal.getId(), meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("deleting meal id " + id);
        return isOwner(repository.get(id),userId)&&repository.remove(id)!=null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal id ",id);
        Meal m = repository.get(id);
        return isOwner(m,userId) ? m : null;
    }

    @Override
    public List<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("get all meals");
        List<Meal> meals = repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .filter(m -> DateTimeUtil.isBetween(m.getDate(), startDate,endDate))
                .sorted(MealsUtil.FIRST_NEW_DATE)
                .collect(Collectors.toList());
         return meals;
    }
    @Override
    public List<Meal> getAll(int userId) {
        log.info("get all meals");
        List<Meal> meals = repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .sorted(MealsUtil.FIRST_NEW_DATE)
                .collect(Collectors.toList());
        return meals;
    }

    private static boolean isOwner(Meal meal, int userId) {
        return (meal==null)||(meal.getUserId() == userId);
    }
}

