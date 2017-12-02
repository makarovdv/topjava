package ru.javawebinar.topjava.service.Meal;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"jdbc","postgres"})
public class MealServiceJdbcPostgresTest extends MealServiceAbstractTest {
}
