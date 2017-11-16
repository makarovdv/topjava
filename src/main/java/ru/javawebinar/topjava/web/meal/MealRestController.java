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
import java.util.List;

import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;
    private final Logger log = LoggerFactory.getLogger(getClass());
    public List<MealWithExceed> getAll(){
        log.info("getAll");
        List<Meal> meals = service.getAll(AuthorizedUser.id());
        return MealsUtil.getWithExceeded(meals,MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id,AuthorizedUser.id());
    }
    public List<MealWithExceed> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
        log.info("getAll with arguments");
        List<Meal> meals = service.getAllFiltered(AuthorizedUser.id(),startDate,endDate);
        return MealsUtil.getFilteredWithExceeded(meals,startTime,endTime,MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(AuthorizedUser.id());
        return service.create(meal,AuthorizedUser.id());
    }
    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id,AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, meal.getUserId());
        assureIdConsistent(meal, id);
        meal.setUserId(AuthorizedUser.id());
        service.update(meal,AuthorizedUser.id());
    }
}