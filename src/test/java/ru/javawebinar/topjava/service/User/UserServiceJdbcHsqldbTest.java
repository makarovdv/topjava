package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"hsqldb","jdbc"})
public class UserServiceJdbcHsqldbTest extends UserServiceAbstractTest {
}
