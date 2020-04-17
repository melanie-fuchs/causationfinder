package ch.yoursource.causationfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.yoursource.causationfinder.entity.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {

        @Query("SELECT t FROM ConfirmationToken t WHERE t.confirmationToken = :confirmationToken")
       public ConfirmationToken findByConfirmationToken(@Param("confirmationToken") String confirmationToken);
    
}
