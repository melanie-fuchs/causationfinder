package ch.yoursource.causationfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.yoursource.causationfinder.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
}
