package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;

@Profile(Profiles.POSTGRES_DB)
@Repository
public class JdbcPostgresMealRepositoryImpl extends AbstractJdbcMealRepository<LocalDateTime>{
    @Override
    protected LocalDateTime getCorrectDateTime(LocalDateTime dateTime) {
        return dateTime;
    }
}
