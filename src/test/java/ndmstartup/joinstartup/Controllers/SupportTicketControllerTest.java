package ndmstartup.joinstartup.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Services.Interfaces.SupportTicketService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SupportTicketController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SupportTicketControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private SupportTicketService supportTicketService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void getSupportTicketByTicketId_Success() throws Exception {
		GetSupportTicketDTO supportTicketDTO = GetSupportTicketDTO.builder()
				.id(1L)
				.name("Test Ticket")
				.description("Test Description")
				.ticketStatus("Open")
				.build();

		Mockito.when(supportTicketService.getSupportTicketByTicketId(Mockito.anyLong()))
				.thenReturn(supportTicketDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/support-ticket/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Test Ticket"))
				.andExpect(jsonPath("$.description").value("Test Description"))
				.andExpect(jsonPath("$.ticketStatus").value("Open"));

		Mockito.verify(supportTicketService).getSupportTicketByTicketId(Mockito.eq(1L));
	}

	@Test
	void getSupportTicketByTicketId_NotFound() throws Exception {
		Mockito.when(supportTicketService.getSupportTicketByTicketId(Mockito.anyLong()))
				.thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.get("/support-ticket/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(supportTicketService).getSupportTicketByTicketId(Mockito.eq(1L));
	}

	@Test
	void getSupportTicketByStatus_Success() throws Exception {
		List<GetSupportTicketDTO> supportTickets = List.of(
				GetSupportTicketDTO.builder().id(1L).name("Ticket 1").ticketStatus("Open").build(),
				GetSupportTicketDTO.builder().id(2L).name("Ticket 2").ticketStatus("Open").build()
		);

		Mockito.when(supportTicketService.getAllSupportTicketsByStatus(Mockito.anyString()))
				.thenReturn(supportTickets);

		mockMvc.perform(MockMvcRequestBuilders.get("/support-ticket/status/Open")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Ticket 1"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Ticket 2"));

		Mockito.verify(supportTicketService).getAllSupportTicketsByStatus(Mockito.eq("Open"));
	}

	@Test
	void getSupportTicketByStatus_NotFound() throws Exception{
		Mockito.when(supportTicketService.getAllSupportTicketsByStatus(Mockito.anyString()))
				.thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/support-ticket/status/InvalidStatus")
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));

		Mockito.verify(supportTicketService).getAllSupportTicketsByStatus(Mockito.eq("InvalidStatus"));
	}

	@Test
	void getAllSupportTickets_Success() throws Exception {
		List<GetSupportTicketDTO> supportTickets = List.of(
				GetSupportTicketDTO.builder().id(1L).name("Ticket 1").ticketStatus("Open").build(),
				GetSupportTicketDTO.builder().id(2L).name("Ticket 2").ticketStatus("Closed").build()
		);

		Mockito.when(supportTicketService.getAllSupportTickets()).thenReturn(supportTickets);

		mockMvc.perform(MockMvcRequestBuilders.get("/support-ticket/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Ticket 1"))
				.andExpect(jsonPath("$[0].ticketStatus").value("Open"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Ticket 2"))
				.andExpect(jsonPath("$[1].ticketStatus").value("Closed"));

		Mockito.verify(supportTicketService).getAllSupportTickets();
	}

	@Test
	void getAllSupportTickets_EmptyList() throws Exception {
		Mockito.when(supportTicketService.getAllSupportTickets()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/support-ticket/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0));

		Mockito.verify(supportTicketService).getAllSupportTickets();
	}

	@Test
	void deleteSupportTicket_Success() throws Exception {
		Mockito.doNothing().when(supportTicketService).deleteSupportTicket(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/support-ticket/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(supportTicketService).deleteSupportTicket(Mockito.eq(1L));
	}

	@Test
	void deleteSupportTicket_NotFound() throws Exception {
		Mockito.doThrow(NoSuchElementException.class).when(supportTicketService).deleteSupportTicket(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/support-ticket/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(supportTicketService).deleteSupportTicket(Mockito.eq(1L));
	}

	@Test
	void changeSupportTicketStatus_Success() throws Exception {
		GetSupportTicketDTO updatedTicket = GetSupportTicketDTO.builder()
				.id(1L)
				.name("Ticket 1")
				.description("Test ticket")
				.ticketStatus("Closed")
				.build();

		Mockito.when(supportTicketService.changeSupportTicketStatus(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(updatedTicket);

		mockMvc.perform(MockMvcRequestBuilders.put("/support-ticket/1/status")
						.param("status", "Closed")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Ticket 1"))
				.andExpect(jsonPath("$.description").value("Test ticket"))
				.andExpect(jsonPath("$.ticketStatus").value("Closed"));

		Mockito.verify(supportTicketService).changeSupportTicketStatus(Mockito.eq(1L), Mockito.eq("Closed"));
	}

	@Test
	void changeSupportTicketStatus_NotFound() throws Exception {
		Mockito.when(supportTicketService.changeSupportTicketStatus(Mockito.anyLong(), Mockito.anyString()))
				.thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.put("/support-ticket/1/status")
						.param("status", "Closed")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(supportTicketService).changeSupportTicketStatus(Mockito.eq(1L), Mockito.eq("Closed"));
	}

}
