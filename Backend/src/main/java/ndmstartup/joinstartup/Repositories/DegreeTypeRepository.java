package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.DegreeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegreeTypeRepository extends JpaRepository<DegreeType, Long> {
}
