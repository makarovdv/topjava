package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"postgres","datajpa"})
public class UserServiceSpringDataJpaPostgresTest extends UserServiceAbstractTest {
}
