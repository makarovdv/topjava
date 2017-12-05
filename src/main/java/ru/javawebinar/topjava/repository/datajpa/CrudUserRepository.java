package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.User;

import java.util.List;
import java.util.Optional;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(name = User.DELETE)
    int delete(@Param("id") int id);

    @Modifying
    @Override
    User save(User user);

    @Override
    Optional<User> findById(Integer id);

    @Override
    List<User> findAll(Sort sort);

    User getByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.meals m WHERE u.id=?1 ORDER BY m.dateTime DESC")
    User getUserWithMeals(int id);
}
