package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_1_USER.getId(),USER_ID);
        assertMatch(meal, MEAL_1_USER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL_1_USER.getId(),ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_7_ADMIN.getId(),ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID),MEAL_9_ADMIN, MEAL_8_ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(MEAL_1_USER.getId(),ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> all = service.getBetweenDates(START_DATE,END_DATE,USER_ID);
        assertMatch(all, MEAL_3_USER, MEAL_2_USER,MEAL_1_USER);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> all = service.getBetweenDateTimes(START_DATE_TIME,END_DATE_TIME,USER_ID);
        assertMatch(all, MEAL_2_USER,MEAL_1_USER);

    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, MEAL_9_ADMIN,MEAL_8_ADMIN,MEAL_7_ADMIN);
    }

    @Test
    public void notNullGetAll() throws Exception {
        List<Meal> all = service.getAll(3);
        assertMatch(all, Collections.emptyList());
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL_1_USER);
        updated.setCalories(1);
        service.update(updated,USER_ID);
        assertMatch(service.get(MEAL_1_USER.getId(),USER_ID),updated);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() throws Exception {
        Meal updated = new Meal(MEAL_1_USER);
        updated.setCalories(1);
        service.update(updated,ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null,
                LocalDateTime.of(2017,05,30,12,00,00),
                "Новая еда",
                500);
        Meal created = service.create(newMeal,USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL_6_USER,MEAL_5_USER,
                MEAL_4_USER,MEAL_3_USER,MEAL_2_USER,MEAL_1_USER);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeForUserCreate() throws Exception {
        service.create(new Meal(null,
                MEAL_1_USER.getDateTime(),
                "Новая еда",
                500)
                ,USER_ID);
    }
    @Test
    public void duplicateDateTimeForAnotherUserCreate() throws Exception {
        service.create(new Meal(null,
                        MEAL_1_USER.getDateTime(),
                        "Новая еда",
                        500)
                ,ADMIN_ID);
    }
}