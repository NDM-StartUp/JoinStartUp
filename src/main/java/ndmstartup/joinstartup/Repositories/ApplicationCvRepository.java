package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ApplicationCv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationCvRepository extends JpaRepository<ApplicationCv, Long> {
	Optional<ApplicationCv> findById(Long id);
}
