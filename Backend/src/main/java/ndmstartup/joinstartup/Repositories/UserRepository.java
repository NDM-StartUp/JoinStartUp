package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
