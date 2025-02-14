package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.Mappers.SupportTicketMapper;
import ndmstartup.joinstartup.Models.SupportTicket;
import ndmstartup.joinstartup.Models.TicketStatus;
import ndmstartup.joinstartup.Repositories.SupportTicketRepository;
import ndmstartup.joinstartup.Repositories.TicketStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SupportTicketServiceImplTest {
	@Mock
	private SupportTicketRepository supportTicketRepository;
	@Mock
	private TicketStatusRepository ticketStatusRepository;
	@Mock
	private SupportTicketMapper supportTicketMapper;
	@InjectMocks
	private SupportTicketServiceImpl supportTicketService;

	@Test
	void getSupportTicketByTicketId_Success() {
		Long ticketId = 1L;

		SupportTicket supportTicket = new SupportTicket();
		supportTicket.setId(ticketId);
		supportTicket.setName("Name 1");
		supportTicket.setDescription("Desc 1");

		GetSupportTicketDTO expectedDTO = GetSupportTicketDTO.builder()
				.id(ticketId)
				.name("Name 1")
				.description("Desc 1")
				.ticketStatus("Open")
				.build();

		when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.of(supportTicket));
		when(supportTicketMapper.entityToDTO(supportTicket)).thenReturn(expectedDTO);

		GetSupportTicketDTO result = supportTicketService.getSupportTicketByTicketId(ticketId);

		verify(supportTicketRepository, times(1)).findById(ticketId);
		verify(supportTicketMapper, times(1)).entityToDTO(supportTicket);
		assertNotNull(result);
		assertEquals(expectedDTO, result);
	}

	@Test
	void getSupportTicketByTicketId_NotFound_ThrowsNoSuchElementException() {
		Long ticketId = 1L;
		when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> supportTicketService.getSupportTicketByTicketId(ticketId));

		assertEquals("Support ticket not found with id" + ticketId, exception.getMessage());
		verify(supportTicketRepository, times(1)).findById(ticketId);
		verify(supportTicketMapper, never()).entityToDTO(any());
	}

	private SupportTicket createSupportTicket(Long id, String name, String description) {
		SupportTicket supportTicket = new SupportTicket();
		supportTicket.setId(id);
		supportTicket.setName(name);
		supportTicket.setDescription(description);

		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setId(1L);
		ticketStatus.setName("Open");

		supportTicket.setTicketStatus(ticketStatus);

		return supportTicket;
	}
	@Test
	void getAllSupportTicketsByStatus_Success() {
		String status = "Open";

		List<SupportTicket> tickets = new ArrayList<>();
		tickets.add(createSupportTicket(1L, "Login Issue", "User cannot log in"));
		tickets.add(createSupportTicket(2L, "Server Down", "Server not responding"));

		List<GetSupportTicketDTO> expectedDTOs = new ArrayList<>();
		GetSupportTicketDTO supportTicketDTO1 = GetSupportTicketDTO.builder().
				id(1L).
				name("Login Issue").
				description("User cannot log in").
				ticketStatus("Open").
				build();
		GetSupportTicketDTO supportTicketDTO2 = GetSupportTicketDTO.builder().
				id(1L).
				name("Server Down").
				description("Server not responding").
				ticketStatus("Open").
				build();
		expectedDTOs.add(supportTicketDTO1);
		expectedDTOs.add(supportTicketDTO2);

		when(supportTicketRepository.findAllByStatusName(status)).thenReturn(tickets);
		when(supportTicketMapper.entityToDTO(tickets.get(0))).thenReturn(expectedDTOs.get(0));
		when(supportTicketMapper.entityToDTO(tickets.get(1))).thenReturn(expectedDTOs.get(1));

		List<GetSupportTicketDTO> result = supportTicketService.getAllSupportTicketsByStatus(status);

		verify(supportTicketRepository, times(1)).findAllByStatusName(status);
		verify(supportTicketMapper, times(2)).entityToDTO(any(SupportTicket.class));
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(expectedDTOs, result);
	}

	@Test
	void getAllSupportTicketsByStatus_NoTicketsFound_ReturnsEmptyList() {
		String status = "Closed";
		when(supportTicketRepository.findAllByStatusName(status)).thenReturn(new ArrayList<>());

		List<GetSupportTicketDTO> result = supportTicketService.getAllSupportTicketsByStatus(status);

		verify(supportTicketRepository, times(1)).findAllByStatusName(status);
		verify(supportTicketMapper, never()).entityToDTO(any());
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void getAllSupportTickets_Success() {
		List<SupportTicket> tickets = new ArrayList<>();
		tickets.add(createSupportTicket(1L, "Login Issue", "User cannot log in"));
		tickets.add(createSupportTicket(2L, "Server Down", "Server not responding"));

		List<GetSupportTicketDTO> expectedDTOs = new ArrayList<>();
		GetSupportTicketDTO supportTicketDTO1 = GetSupportTicketDTO.builder().
				id(1L).
				name("Login Issue").
				description("User cannot log in").
				ticketStatus("Open").
				build();
		GetSupportTicketDTO supportTicketDTO2 = GetSupportTicketDTO.builder().
				id(1L).
				name("Server Down").
				description("Server not responding").
				ticketStatus("Open").
				build();
		expectedDTOs.add(supportTicketDTO1);
		expectedDTOs.add(supportTicketDTO2);

		when(supportTicketRepository.findAll()).thenReturn(tickets);
		when(supportTicketMapper.entityToDTO(tickets.get(0))).thenReturn(expectedDTOs.get(0));
		when(supportTicketMapper.entityToDTO(tickets.get(1))).thenReturn(expectedDTOs.get(1));

		List<GetSupportTicketDTO> result = supportTicketService.getAllSupportTickets();

		verify(supportTicketRepository, times(1)).findAll();
		verify(supportTicketMapper, times(2)).entityToDTO(any(SupportTicket.class));
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(expectedDTOs, result);
	}

	@Test
	void getAllSupportTicketsB_NoTicketsFound_ReturnsEmptyList() {
		when(supportTicketRepository.findAll()).thenReturn(new ArrayList<>());

		List<GetSupportTicketDTO> result = supportTicketService.getAllSupportTickets();

		verify(supportTicketRepository, times(1)).findAll();
		verify(supportTicketMapper, never()).entityToDTO(any());
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void deleteSupportTicket_Success() {
		Long ticketId = 1L;
		SupportTicket existingSupportTicket = SupportTicket.builder().id(ticketId).build();

		when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.of(existingSupportTicket));

		supportTicketService.deleteSupportTicket(ticketId);

		verify(supportTicketRepository, times(1)).findById(ticketId);
		verify(supportTicketRepository, times(1)).delete(existingSupportTicket);
	}

	@Test
	void deleteSupportTicket_SupportTicketNotFound_ThrowsNoSuchElementException() {
		Long ticketId = 1L;
		when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> supportTicketService.deleteSupportTicket(ticketId));

		assertEquals("Support ticket not found with id" + ticketId, exception.getMessage());
		verify(supportTicketRepository, times(1)).findById(ticketId);
		verify(supportTicketRepository, never()).delete(any());
	}

	@Test
	void changeSupportTicketStatus_Success() {
		Long ticketId = 1L;
		String newStatus = "Resolved";

		TicketStatus ticketStatus = new TicketStatus();
		ticketStatus.setName("Open");
		SupportTicket supportTicket = SupportTicket.builder().
				id(ticketId).
				name("Login Issue").
				description("User cannot log in").
				ticketStatus(ticketStatus).build();

		TicketStatus expectedStatus = new TicketStatus();
		expectedStatus.setName(newStatus);
		GetSupportTicketDTO expectedDTO = GetSupportTicketDTO.builder()
				.id(ticketId)
				.name("Login Issue")
				.description("User cannot log in")
				.ticketStatus(newStatus)
				.build();

		when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.of(supportTicket));
		when(ticketStatusRepository.findByName(newStatus)).thenReturn(Optional.of(expectedStatus));
		when(supportTicketRepository.save(supportTicket)).thenReturn(supportTicket);
		when(supportTicketMapper.entityToDTO(supportTicket)).thenReturn(expectedDTO);

		GetSupportTicketDTO result = supportTicketService.changeSupportTicketStatus(ticketId, newStatus);

		verify(supportTicketRepository, times(1)).findById(ticketId);
		verify(ticketStatusRepository, times(1)).findByName(newStatus);
		verify(supportTicketRepository, times(1)).save(supportTicket);
		verify(supportTicketMapper, times(1)).entityToDTO(supportTicket);
		assertNotNull(result);
		assertEquals(expectedDTO, result);
	}

	@Test
	void changeSupportTicketStatus_TicketNotFound_ThrowsNoSuchElementException() {
		Long ticketId = 1L;
		String newStatus = "Resolved";

		when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> supportTicketService.changeSupportTicketStatus(ticketId, newStatus));

		assertEquals("Support ticket not found with id" + ticketId, exception.getMessage());
		verify(supportTicketRepository, times(1)).findById(ticketId);
		verify(ticketStatusRepository, never()).findByName(any());
		verify(supportTicketRepository, never()).save(any());
		verify(supportTicketMapper, never()).entityToDTO(any());
	}

	@Test
	void changeSupportTicketStatus_StatusNotFound_ThrowsNoSuchElementException() {
		Long ticketId = 1L;
		String newStatus = "Resolved";

		SupportTicket supportTicket = SupportTicket.builder().
				id(ticketId).
				name("Login Issue").
				build();

		when(supportTicketRepository.findById(ticketId)).thenReturn(Optional.of(supportTicket));
		when(ticketStatusRepository.findByName(newStatus)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> supportTicketService.changeSupportTicketStatus(ticketId, newStatus));

		assertEquals("Status name not found with name" + newStatus, exception.getMessage());
		verify(supportTicketRepository, times(1)).findById(ticketId);
		verify(ticketStatusRepository, times(1)).findByName(newStatus);
		verify(supportTicketRepository, never()).save(any());
		verify(supportTicketMapper, never()).entityToDTO(any());
	}
}
