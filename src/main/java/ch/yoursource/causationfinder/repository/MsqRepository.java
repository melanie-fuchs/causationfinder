package ch.yoursource.causationfinder.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.yoursource.causationfinder.entity.MedicalSymptomsQuestionnaire;
import ch.yoursource.causationfinder.entity.User;

public interface MsqRepository extends JpaRepository<MedicalSymptomsQuestionnaire, Integer> {

    // find by username and date
    @Query("SELECT m FROM MedicalSymptomsQuestionnaire m WHERE m.user = :user AND m.date = :date")
    public MedicalSymptomsQuestionnaire findByUserAndDate(@Param("user") User user, @Param("date") Date date);
}
