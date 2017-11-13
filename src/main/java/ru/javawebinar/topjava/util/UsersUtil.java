package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;

public class UsersUtil {
    public static final Comparator<User> nameAlphabetOrder = (u1, u2) -> {
        int name = u1.getName().compareTo(u2.getName());
        if (name!=0) return name;
        int email = u1.getEmail().compareTo(u2.getEmail());
        if (email!=0) return email;
        int calories = Integer.compare(u1.getCaloriesPerDay() ,u2.getCaloriesPerDay());
        if (calories!=0) return calories;
        int registrationDate = u1.getRegistered().compareTo(u2.getRegistered());
        if (registrationDate!=0) return registrationDate;
        int id = Integer.compare(u1.getId(),u2.getId());
        return id;
    };
    public static final List<User> USERS = Arrays.asList(
       new User(null,"Николай","nikolai@mail.ru","password", Role.USER),
       new User(null,"Олег","oleg@yandex.ru","password", Role.ADMIN),
       new User(null,"Оксана","oxana@gmail.com","password", Role.USER)
    );
    public static User getFirstByEmail(Collection<User> users, String email){
        return users.stream()
             .filter(u -> u.getEmail().equals(email))
             .findFirst()
             .get();
    }
}
