package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, Long> {
	Optional<ApplicationStatus> findByName(String name);
}
