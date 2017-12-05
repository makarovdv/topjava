package ru.javawebinar.topjava.service.User;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.JPA})
public class UserServiceHibernateTest extends AbstractUserServiceTest {
}
