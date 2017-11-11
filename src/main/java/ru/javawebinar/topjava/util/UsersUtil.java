package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsersUtil {
    public static final Comparator<User> compareByName = Comparator.comparing(AbstractNamedEntity::getName);
    public static final List<User> USERS = Arrays.asList(
       new User(null,"Николай","nikolai@mail.ru","password", Role.USER),
       new User(null,"Олег","oleg@yandex.ru","password", Role.ADMIN),
       new User(null,"Оксана","oxana@gmail.com","password", Role.USER)
    );
    public static List<User> getSortedByName(Stream<User> users){
        return users.sorted(compareByName)
             .collect(Collectors.toList());
    }
    public static User getFirstByEmail(Collection<User> users, String email){
        return users.stream()
             .filter(u -> u.getEmail().equals(email))
             .findFirst()
             .get();
    }
}
