package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
}
