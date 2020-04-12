package ch.yoursource.causationfinder.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.yoursource.causationfinder.entity.ObservedDayValue;
import ch.yoursource.causationfinder.entity.User;

public interface ObservedDayValueRepository extends JpaRepository<ObservedDayValue, Integer>{

    // find all observed values from a given user on a given date
    @Query("SELECT o FROM ObservedDayValue o LEFT JOIN CustomParameter c ON o.customParameter = c WHERE o.date = :date AND c.user = :user")
    public List<ObservedDayValue> findByDateAndUser(@Param("date") Date date, @Param("user") User user);
}
