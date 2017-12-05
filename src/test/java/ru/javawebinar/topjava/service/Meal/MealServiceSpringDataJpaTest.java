package ru.javawebinar.topjava.service.Meal;

import org.junit.Test;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatchWithUser;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.DATAJPA})
public class MealServiceSpringDataJpaTest extends AbstractMealServiceTest {
    @Test
    public void testGetWithUser() throws Exception {
        Meal actual = service.getMealByIdWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        ADMIN_MEAL1.setUser(UserTestData.ADMIN);
        assertMatchWithUser(actual, ADMIN_MEAL1);
    }

    @Test
    public void testGetWithUserNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getMealByIdWithUser(ADMIN_MEAL_ID, USER_ID);
    }
}
