package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import org.junit.Test;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ActiveProfiles({Profiles.DATAJPA})
public class UserServiceSpringDataJpaTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() throws Exception {
        User user = service.getUserWithMeals(USER_ID);
        User expected = new User(USER);
        expected.setMeals(MEALS);
        assertMatch(user, USER);
        assertMatch(user.getMeals(),expected.getMeals());
    }

    @Test
    public void getWithEmptyMeals() throws Exception {
        User newUser = new User(START_SEQ+10, "User", "user2@yandex.ru", "password", Role.ROLE_USER);
        service.create(newUser);
        User user = service.getUserWithMeals(START_SEQ+10);
        if (!user.getMeals().isEmpty()){
            throw new Exception("meals are not empty");
        }
    }

    @Test
    public void getByIdWithMealsNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getUserWithMeals(START_SEQ+11);
    }

    @Override
    public void update() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        service.update(updated);
        assertMatch(service.get(USER_ID), updated);
        User expected = new User(USER);
        assertMatch(updated.getMeals(),expected.getMeals());
    }
}