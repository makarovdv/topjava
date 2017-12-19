package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Transactional(readOnly = true)
@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final UserWithRolesExtractor extractor = new UserWithRolesExtractor();

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final UsersComparator comparator = new UsersComparator();

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
            if(comparator.compare(inBase,user)!=0) {
                int rows = namedParameterJdbcTemplate.update("" +
                        "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay " +
                        "WHERE id=:id", parameterSource);
                if (rows == 0) {
                    return null;
                }
            }
            if(!user.getRoles().containsAll(inBase.getRoles())||
                    !inBase.getRoles().containsAll(user.getRoles())){
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
                "left join user_roles r on u.id = r.user_id";
        return namedParameterJdbcTemplate.query(sql, extractor);
    }

    private void deleteRoles(User user){
        Integer id = user.getId();
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", id);
    }

    private void insertRoles(User user){
        final List<Role> roles = new ArrayList<>(user.getRoles());
        Integer id = user.getId();
        String sql = "INSERT INTO user_roles (role, user_id) VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = roles.get(i);
                ps.setString(1, role.toString());
                ps.setInt(2,id);
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }
}