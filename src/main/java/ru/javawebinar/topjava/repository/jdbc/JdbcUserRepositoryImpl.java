package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Transactional(readOnly = true)
@Repository
public class JdbcUserRepositoryImpl implements UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private static final ResultSetExtractor<List<User>> extractor = new ResultSetExtractor<List<User>>() {
        @Nullable
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> users = new LinkedHashMap<>();
            User user;
            while (rs.next()){
                Integer id = rs.getInt("id");
                user = users.get(id);
                if (user==null){
                    user = ROW_MAPPER.mapRow(rs,rs.getRow());
                    user.setRoles(EnumSet.noneOf(Role.class));
                    users.put(id,user);
                }
                HashSet<Role> roles = new HashSet<>(user.getRoles());
                roles.add(Role.valueOf(rs.getString("role")));
                user.setRoles(EnumSet.copyOf(roles));
            }
            return new ArrayList<>(users.values());
        }
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            User inBase = get(user.getId());
            if (!equalsIgnoreRolesAndId(inBase,user)) {
                int rows = namedParameterJdbcTemplate.update("" +
                        "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay " +
                        "WHERE id=:id", parameterSource);
                if (rows == 0) {
                    return null;
                }
            }
            if (!user.getRoles().equals(inBase.getRoles())){
                deleteRoles(user);
                insertRoles(user);
            }
        }
        return user;
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
            String sql = "SELECT * FROM users u " +
                    "     left join user_roles r on u.id = r.user_id " +
                    "     WHERE u.id = :id";
            Map<String, Integer> map = Collections.singletonMap("id", id);
            List<User> users = namedParameterJdbcTemplate.query(sql, map, extractor);
            return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users u " +
                "     left join user_roles r on u.id = r.user_id " +
                "     WHERE email = :email";
        Map<String, String> map = Collections.singletonMap("email", email);
        List<User> users = namedParameterJdbcTemplate.query(sql, map, extractor);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users u " +
                "     left join user_roles r on u.id = r.user_id " +
                "     ORDER BY u.name, u.email";
        return namedParameterJdbcTemplate.query(sql, extractor);
    }

    private void deleteRoles(User user){
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
    }

    private void insertRoles(User user){
        List<Object[]> batchArgs = new ArrayList<>();
        new ArrayList<>(user.getRoles())
                .forEach(r -> batchArgs.add(new Object[]{r.toString(),user.getId()}));
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (role, user_id) VALUES (?, ?)",batchArgs);
    }

    private boolean equalsIgnoreRolesAndId(User u1, User u2){
        if (!u1.getName().equals(u2.getName())) return false;
        if (!u1.getEmail().equals(u2.getEmail())) return false;
        if (!u1.getRegistered().equals(u2.getRegistered())) return false;
        if (u1.isEnabled() != u2.isEnabled()) return false;
        if (u1.getCaloriesPerDay() != u2.getCaloriesPerDay()) return false;
        return u1.getPassword().equals(u2.getPassword());
    }
}