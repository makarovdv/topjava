package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 10, 0,0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 30, 13, 0, 0);
    public static final LocalDate START_DATE = LocalDate.of(2015, Month.MAY, 29);
    public static final LocalDate END_DATE = LocalDate.of(2015, Month.MAY, 30);

    public static final Meal MEAL_1_USER = new Meal(START_SEQ + 2,LocalDateTime.of(2015, Month.MAY, 30, 10, 0,0), "Завтрак", 500);
    public static final Meal MEAL_2_USER = new Meal(START_SEQ + 3,LocalDateTime.of(2015, Month.MAY, 30, 13, 0, 0), "Обед", 1000);
    public static final Meal MEAL_3_USER = new Meal(START_SEQ + 4,LocalDateTime.of(2015, Month.MAY, 30, 20, 0, 0), "Ужин", 500);
    public static final Meal MEAL_4_USER = new Meal(START_SEQ + 5,LocalDateTime.of(2015, Month.MAY, 31, 10, 0, 0), "Завтрак", 1000);
    public static final Meal MEAL_5_USER = new Meal(START_SEQ + 6,LocalDateTime.of(2015, Month.MAY, 31, 13, 0, 0), "Обед", 500);
    public static final Meal MEAL_6_USER = new Meal(START_SEQ + 7,LocalDateTime.of(2015, Month.MAY, 31, 20, 0, 0), "Ужин", 510);
    public static final Meal MEAL_7_ADMIN = new Meal(START_SEQ + 8,LocalDateTime.of(2009, Month.JUNE, 04, 10, 0, 0), "Завтрак", 700);
    public static final Meal MEAL_8_ADMIN = new Meal(START_SEQ + 9,LocalDateTime.of(2009, Month.JUNE, 04, 13, 0, 0), "Обед", 700);
    public static final Meal MEAL_9_ADMIN = new Meal(START_SEQ + 10,LocalDateTime.of(2009, Month.JUNE, 04, 20, 0, 0), "Ужин", 700);

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}
