package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.SupportTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class SupportTicketRepositoryTest {
	@Autowired
	private SupportTicketRepository supportTicketRepository;

	@Test
	public void testFindAllByUserId_ReturnsAllSupportTickets_WhenExist(){
		List<SupportTicket> tickets = supportTicketRepository.findAllByUserId(1L);

		assertNotNull(tickets);
		assertFalse(tickets.isEmpty());
		assertEquals(1, tickets.size());
		assertEquals("Issue 1", tickets.get(0).getName());
		assertEquals("Problem with application", tickets.get(0).getDescription());
	}

	@Test
	public void testFindAllByUserId_ReturnsAllSupportTickets_WhenNotExist(){
		List<SupportTicket> tickets = supportTicketRepository.findAllByUserId(999L);

		assertNotNull(tickets);
		assertTrue(tickets.isEmpty());
	}

	@Test
	public void testFindAllByStatusName_ReturnsAllSupportTickets_WhenExist() {

		List<SupportTicket> tickets = supportTicketRepository.findAllByStatusName("Closed");

		assertNotNull(tickets);
		assertFalse(tickets.isEmpty());
		assertEquals(1, tickets.size());
		assertEquals("Issue 2", tickets.get(0).getName());
		assertEquals("Problem with account", tickets.get(0).getDescription());
	}

	@Test
	public void testFindAllByStatusName_ReturnsAllSupportTickets_WhenNotExist() {

		List<SupportTicket> tickets = supportTicketRepository.findAllByStatusName("Resolved");

		assertNotNull(tickets);
		assertTrue(tickets.isEmpty());
	}
}
