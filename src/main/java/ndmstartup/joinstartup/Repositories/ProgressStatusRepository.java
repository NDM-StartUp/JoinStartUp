package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressStatusRepository extends JpaRepository<ProgressStatus, Long> {
	Optional<ProgressStatus> findByName(String name);
}
