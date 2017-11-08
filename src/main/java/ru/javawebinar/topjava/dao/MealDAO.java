package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    public void add(Meal m);
    public void update(Meal m);
    public List<Meal> getAll();
    public Meal getById(int id);
    public void remove(int id);
}
