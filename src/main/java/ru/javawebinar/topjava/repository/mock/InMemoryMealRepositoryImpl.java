package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

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
        MealsUtil.MEALS.forEach(m -> save(m,m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            log.info("save new meal " + meal);
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        } else {
            log.info("update meal " + meal);
        }
        if(!ValidationUtil.isOwner(meal,userId)) return null;
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("deleting meal id " + id);
        if(get(id,userId)==null) return false;
        repository.remove(id);
        return true;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal id ",id);
        Meal m = repository.get(id);
        if(!ValidationUtil.isOwner(m,userId)) return null;
        return m;
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("get all meals");
        List<Meal> meals = repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .filter(m -> DateTimeUtil.isBetweenDate(m.getDate(), startDate,endDate))
                .sorted(MealsUtil.FIRST_NEW_DATE)
                .collect(Collectors.toList());
         return meals;
    }
}

