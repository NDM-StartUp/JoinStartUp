package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findAllByUserId(long userId);

    @Query("SELECT st FROM SupportTicket st WHERE st.ticketStatus.name = :status")
    List<SupportTicket> findAllByStatusName(@Param("status") String status);
}
