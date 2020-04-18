package ch.yoursource.causationfinder.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.ObservedDayValue;
import ch.yoursource.causationfinder.entity.User;

public interface ObservedDayValueRepository extends JpaRepository<ObservedDayValue, Integer>{

    // find all observed values from a given user on a given date
    @Query("SELECT o FROM ObservedDayValue o INNER JOIN CustomParameter c ON o.customParameter = c WHERE o.date = :date AND c.user = :user")
    public List<ObservedDayValue> findByDateAndUser(@Param("date") Date date, @Param("user") User user);

    // find all boolean and numeric observeday values where:
    // customparameters are currently set active=true
    // within range of a given start-and end-date
    // get min. and max-value
    // from a given user
    // SELECT o.* FROM ObservedDayValue o INNER JOIN CustomParameter c ON o.custom_parameter_id = c.id
    @Query("SELECT o, c FROM ObservedDayValue o INNER JOIN CustomParameter c ON o.customParameter = c "
            + "WHERE o.date BETWEEN :startDate AND :endDate "
            + "AND c.user = :user "
            + "AND c.active = true")
    public List<ObservedDayValue> findActiveByUserInRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("user") User user);
    
    @Query("SELECT MIN(o.numericValue) FROM ObservedDayValue o WHERE o.customParameter = :customParameter")
    public Double findLowestValueByCustomParameter(@Param("customParameter") CustomParameter customParameter);
    
    @Query("SELECT MAX(o.numericValue) FROM ObservedDayValue o WHERE o.customParameter = :customParameter")
    public Double findHighestValueByCustomParameter(@Param("customParameter") CustomParameter customParameter);
}
