package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile("hsqldb")
public class JdbcHsqlMealRepositoryImpl extends JdbcMealRepositoryAbstract<Timestamp>{
    public JdbcHsqlMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp getCorrectDateTime(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}
