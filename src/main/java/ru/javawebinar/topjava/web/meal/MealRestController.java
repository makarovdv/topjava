package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.MealsUtil;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;
    private final Logger log = LoggerFactory.getLogger(getClass());
    public List<MealWithExceed> getAll(){
        List<Meal> meals = service.getAll(AuthorizedUser.id());
        return MealsUtil.getWithExceeded(meals,MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
    public Meal get(int id) {
        return service.get(id,AuthorizedUser.id());
    }
    public List<MealWithExceed> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
        return Collections.emptyList();
    }

    public Meal create(Meal meal) {
        return service.create(meal,AuthorizedUser.id());
    }
    public void delete(int id) {
        service.delete(id,AuthorizedUser.id());
    }

    public void update(Meal meal) {
        service.update(meal, AuthorizedUser.id());
    }
}