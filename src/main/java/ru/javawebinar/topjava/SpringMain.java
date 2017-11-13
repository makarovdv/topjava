package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ADMIN));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
//            mealRestController.delete(3);
//            mealRestController.delete(3);
            Meal m1 = new Meal(null,
                    LocalDateTime.MAX,
                    "m1",
                    1223);
            Meal m2 = new Meal(1,
                    LocalDateTime.MAX,
                    "m2",
                    1223,2);
            mealRestController.create(m1);
            mealRestController.create(m2);
            mealRestController.update(m1);
//            System.out.printf("method get " + mealRestController.get(2));
//            mealRestController.getAll().forEach(System.out::println);

//            LocalDate startDate = LocalDate.of(2015, Month.MAY, 30);
//            LocalDate endDate = LocalDate.of(2015, Month.MAY, 31);
//            LocalTime startTime = LocalTime.of(11,10);
//            LocalTime endTime = LocalTime.of(15,30);
//            System.out.printf("getAll with parameters:");
//            mealRestController.getAll(startDate,startTime,endDate,endTime).forEach(System.out::println);
        }
    }
}
