package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.TicketStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TicketStatusRepositoryTest {

    @Autowired
    private TicketStatusRepository ticketStatusRepository;

    @Test
    void findByName() {
        Optional<TicketStatus> open = ticketStatusRepository.findByName("Open");
        assertTrue(open.isPresent());
        assertEquals("Open", open.get().getName());
    }

    @Test
    void findByName_NotFound() {
        Optional<TicketStatus> open = ticketStatusRepository.findByName("NotExistingStatus");
        assertTrue(open.isEmpty());
    }
}