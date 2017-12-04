package ru.javawebinar.topjava.service.Meal;

import org.junit.Test;

import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatchWithUser;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public abstract class MealServiceSpringDataJpaAbstractTest extends MealServiceAbstractTest {
    @Test
    public void testGetWithUser() throws Exception {
        Meal actual = service.getMealByIdWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        ADMIN_MEAL1.setUser(UserTestData.ADMIN);
        assertMatchWithUser(actual, ADMIN_MEAL1);
    }
}
