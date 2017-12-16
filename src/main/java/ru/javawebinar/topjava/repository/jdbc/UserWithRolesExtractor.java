package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserWithRolesExtractor implements ResultSetExtractor<List<User>> {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, User> users = new HashMap<>();
        User user = null;
        while (rs.next()){
            Integer id = rs.getInt("id");
            user = users.get(id);
            if (user==null){
                user = ROW_MAPPER.mapRow(rs,rs.getRow());
                user.setRoles(EnumSet.noneOf(Role.class));
                users.put(id,user);
            }
            if (user.getRoles().isEmpty()) {
                user.setRoles(EnumSet.of(Role.valueOf(rs.getString("role"))));
            } else {
                user.setRoles(EnumSet.of(Role.ROLE_ADMIN, Role.ROLE_USER));
            }
        }
        return new ArrayList<>(users.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList()));
    }
}