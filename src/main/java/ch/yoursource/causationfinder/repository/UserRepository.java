package ch.yoursource.causationfinder.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ch.yoursource.causationfinder.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

}
