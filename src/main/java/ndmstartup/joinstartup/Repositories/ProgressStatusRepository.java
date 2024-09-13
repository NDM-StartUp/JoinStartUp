package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgressStatusRepository extends JpaRepository<ProgressStatus, Long> {
	Optional<ProgressStatus> findByName(String name);
}
