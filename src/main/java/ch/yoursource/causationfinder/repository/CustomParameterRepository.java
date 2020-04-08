package ch.yoursource.causationfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.yoursource.causationfinder.entity.CustomParameter;

public interface CustomParameterRepository extends JpaRepository<CustomParameter, Integer> {

}
