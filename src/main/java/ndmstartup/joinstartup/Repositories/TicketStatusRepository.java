package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Long> {
    Optional<TicketStatus> findByName(String name);
}
