package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.User;

import java.util.Comparator;

public class UsersComparator implements Comparator<User> {
    @Override
    public int compare(User u1, User u2) {
        int id = Integer.compare(u1.getId(),u2.getId());
        if (id!=0) return id;
        int name = u1.getName().compareTo(u2.getName());
        if (name!=0) return name;
        int email = u1.getEmail().compareTo(u2.getEmail());
        if (email!=0) return email;
        int date = u1.getRegistered().compareTo(u2.getRegistered());
        if (date!=0) return date;
        int enabled = Boolean.compare(u1.isEnabled(),u2.isEnabled());
        if (enabled!=0) return enabled;
        int calories = Integer.compare(u1.getCaloriesPerDay(),u2.getCaloriesPerDay());
        if (calories!=0) return calories;
        return u1.getPassword().compareTo(u2.getPassword());
    }
}