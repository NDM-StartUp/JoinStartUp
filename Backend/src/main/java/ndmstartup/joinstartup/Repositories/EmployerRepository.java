package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

	boolean existsEmployerById(Long id);
}
