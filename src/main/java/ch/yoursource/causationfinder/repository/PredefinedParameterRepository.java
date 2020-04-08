package ch.yoursource.causationfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.yoursource.causationfinder.entity.PredefinedParameter;

public interface PredefinedParameterRepository extends JpaRepository<PredefinedParameter, Integer> {

}
