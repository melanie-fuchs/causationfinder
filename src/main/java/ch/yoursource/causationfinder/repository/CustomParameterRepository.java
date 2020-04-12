package ch.yoursource.causationfinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.yoursource.causationfinder.entity.CustomParameter;
import ch.yoursource.causationfinder.entity.User;
import ch.yoursource.causationfinder.setup.ParameterType;

public interface CustomParameterRepository extends JpaRepository<CustomParameter, Integer> {

    // find all predefinedParameters per User
    @Query("SELECT c FROM CustomParameter c WHERE c.user = :user AND c.predefinedParam IS NOT NULL")
    public List<CustomParameter> findPredefinedByUser(@Param("user") User user);
    
    // find all CustomParameters per User
    @Query("SELECT c FROM CustomParameter c WHERE c.user = :user AND c.predefinedParam IS NULL")
    public List<CustomParameter> findCustomByUser(@Param("user") User user);
    
    // find all active customParameters per user 
    @Query("SELECT c FROM CustomParameter c WHERE c.user = :user AND c.active=true AND c.type=:type")
    public List<CustomParameter> findActiveByUserAndType(@Param("user") User user, @Param("type") ParameterType type);   
}
 