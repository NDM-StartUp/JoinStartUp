package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StartUpRepository extends JpaRepository<StartUp, Long> {
	Optional<StartUp> findByCompanyName (String companyName);
	Optional<StartUp> findByLocation (String location);
	Optional<StartUp> findByIsPaid (boolean isPaid);
}
