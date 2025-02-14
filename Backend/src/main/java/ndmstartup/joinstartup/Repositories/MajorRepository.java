package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
