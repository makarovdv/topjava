package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal getOneByIdAndUserId(Integer meal, Integer user);

    List<Meal> getAllByUserIdOrderByDateTimeDesc(Integer userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT m FROM Meal m WHERE m.dateTime BETWEEN ?1 AND ?2 AND m.user.id=?3 ORDER BY m.dateTime DESC" )
    List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, Integer userId);

    int deleteByIdAndUserId(Integer meal, Integer user);

    @Query("SELECT m,u FROM Meal m JOIN m.user u WHERE m.id=?1 AND m.user.id=?2")
    Meal getMealByIdWithUser(Integer id, Integer userId);
}
