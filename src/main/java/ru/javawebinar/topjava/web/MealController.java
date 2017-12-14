package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class MealController extends MealRestController {
    @GetMapping
    public String allMeals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/filter")
    public String filteredMeals(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/id{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("meal", super.get(id));
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
            super.create(meal);
        } else {
            super.update(meal,Integer.valueOf(id));
        }
        return "redirect:/meals";
    }
}
