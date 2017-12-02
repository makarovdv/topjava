package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"postgres","jdbc"})
public class UserServiceJdbcPostgresTest extends UserServiceAbstractTest {
}
