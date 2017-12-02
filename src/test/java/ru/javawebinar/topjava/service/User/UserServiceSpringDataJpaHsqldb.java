package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"hsqldb","datajpa"})
public class UserServiceSpringDataJpaHsqldb extends UserServiceAbstractTest {
}
