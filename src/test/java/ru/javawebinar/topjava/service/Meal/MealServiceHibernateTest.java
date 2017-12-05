package ru.javawebinar.topjava.service.Meal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.JPA})
public class MealServiceHibernateTest extends AbstractMealServiceTest {
}
