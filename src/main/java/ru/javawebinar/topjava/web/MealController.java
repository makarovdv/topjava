package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class MealController {
    @Autowired
    private MealService service;

    @GetMapping
    public String allMeals(Model model) {
        List<Meal> meals = service.getAll(AuthorizedUser.id());
        model.addAttribute("meals", MealsUtil.getWithExceeded(meals, AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/filter")
    public String filteredMeals(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> meals = service.getBetweenDates(
                startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                endDate != null ? endDate : DateTimeUtil.MAX_DATE,
                AuthorizedUser.id());
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(
                meals,
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        service.delete(id, AuthorizedUser.id());
        return "redirect:/meals";
    }

//    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
//    public String delete(@PathVariable Integer id) {
//        System.out.println(id);
//        service.delete(id, AuthorizedUser.id());
//        return "redirect:/meals";
//    }

    @GetMapping("/id{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Meal meal = service.get(id, AuthorizedUser.id());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now(), "", 500);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        String id = request.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                String.valueOf(request.getParameter("description")),
                Integer.valueOf(request.getParameter("calories")));
        if ("".equals(id)) {
            service.create(meal, AuthorizedUser.id());
        } else {
            meal.setId(Integer.valueOf(id));
            service.update(meal, AuthorizedUser.id());
        }
        return "redirect:/meals";
    }
}
