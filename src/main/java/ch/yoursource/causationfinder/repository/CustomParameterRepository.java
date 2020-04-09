package ch.yoursource.causationfinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.User;

public interface CustomParameterRepository extends JpaRepository<CustomParameter, Integer> {

    // find all predefinedParameters per User
    @Query("SELECT c FROM CustomParameter c WHERE c.user = :user AND c.predefinedParam IS NOT NULL")
    public List<CustomParameter> findPredefinedByUser(@Param("user") User user);
    
}
