package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.UserServlet;

import org.slf4j.Logger;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDAOTest implements MealDAO {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    public void add(Meal m) {

    }

    @Override
    public void update(Meal m) {

    }

    @Override
    public List<Meal> getAll() {
        return null;
    }

    @Override
    public Meal getById(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }
}
