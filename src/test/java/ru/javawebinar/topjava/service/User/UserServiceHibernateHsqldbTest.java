package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"hsqldb","jpa"})
public class UserServiceHibernateHsqldbTest extends UserServiceAbstractTest {
}
