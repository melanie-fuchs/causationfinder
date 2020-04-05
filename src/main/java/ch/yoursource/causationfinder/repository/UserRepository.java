package ch.yoursource.causationfinder.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ch.yoursource.causationfinder.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.email = :email")
	User findByEmail(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findByUsername(@Param("username") String username);
}
