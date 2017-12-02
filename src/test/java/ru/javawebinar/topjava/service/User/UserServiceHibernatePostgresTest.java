package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"postgres","jpa"})
public class UserServiceHibernatePostgresTest extends UserServiceAbstractTest {
}
