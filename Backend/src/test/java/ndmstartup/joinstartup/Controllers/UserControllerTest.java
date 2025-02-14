package ndmstartup.joinstartup.Controllers;

import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private UserService userService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	void addUser_Success() throws Exception {
		PostUserDTO postUserDTO = PostUserDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@example.com")
				.phone("+123456789")
				.description("A software developer")
				.build();

		Mockito.doNothing().when(userService).addUser(Mockito.any(PostUserDTO.class), Mockito.anyBoolean());

		mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
						.contentType(MediaType.APPLICATION_JSON)
						.param("isEmployee", "true")
						.content(objectMapper.writeValueAsString(postUserDTO)))
				.andExpect(status().isNoContent());

		Mockito.verify(userService).addUser(Mockito.eq(postUserDTO), Mockito.eq(true));
	}

	@Test
	void addUser_ReturnsNotFound() throws Exception {
		PostUserDTO postUserDTO = PostUserDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@example.com")
				.phone("+1234567890")
				.description("Sample description")
				.build();

		Mockito.doThrow(NoSuchElementException.class)
				.when(userService)
				.addUser(Mockito.any(PostUserDTO.class), Mockito.anyBoolean());

		mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
						.contentType(MediaType.APPLICATION_JSON)
						.param("isEmployee", "true")
						.content(objectMapper.writeValueAsString(postUserDTO)))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).addUser(Mockito.any(PostUserDTO.class), Mockito.anyBoolean());
	}

	@Test
	void addUserRole_Success() throws Exception {
		Mockito.doNothing().when(userService).addUserRole(Mockito.anyLong(), Mockito.anyBoolean());

		mockMvc.perform(MockMvcRequestBuilders.post("/users/1/add-role")
						.param("addEmployerRole", "true")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(userService).addUserRole(Mockito.eq(1L), Mockito.eq(true));
	}

	@Test
	void addUserRole_Returns_NotFound() throws Exception {
		Mockito.doThrow(NoSuchElementException.class).when(userService).addUserRole(Mockito.anyLong(), Mockito.anyBoolean());

		mockMvc.perform(MockMvcRequestBuilders.post("/users/1/add-role")
						.param("addEmployerRole", "true")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).addUserRole(Mockito.eq(1L), Mockito.eq(true));
	}

	@Test
	void updateUserData_Success() throws Exception{
		PostUserDTO postUserDTO = PostUserDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@example.com")
				.phone("+123456789")
				.description("Updated user description")
				.build();

		Mockito.doNothing().when(userService).updateUserData(Mockito.anyLong(), Mockito.any(PostUserDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.put("/users/1/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(postUserDTO))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(userService).updateUserData(Mockito.eq(1L),Mockito.eq(postUserDTO));
	}

	@Test
	void updateUserData_Returns_NotFound() throws Exception {
		PostUserDTO postUserDTO = PostUserDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@example.com")
				.phone("+123456789")
				.description("Updated user description")
				.build();

		Mockito.doThrow(NoSuchElementException.class).when(userService).updateUserData(Mockito.anyLong(), Mockito.any(PostUserDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.put("/users/1/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(postUserDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).updateUserData(Mockito.eq(1L), Mockito.eq(postUserDTO));
	}

	@Test
	void deleteUser_Success() throws Exception {
		Mockito.doNothing().when(userService).deleteUser(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/users/1/delete")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(userService).deleteUser(Mockito.eq(1L));
	}

	@Test
	void deleteUser_ReturnsNotFound() throws Exception {
		Mockito.doThrow(NoSuchElementException.class).when(userService).deleteUser(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/users/1/delete")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).deleteUser(Mockito.eq(1L));
	}

	@Test
	void getUserTypes_Success() throws Exception {
		GetUserTypesDTO userTypesDTO = GetUserTypesDTO.builder()
				.isEmployee(true)
				.isEmployer(false)
				.build();

		Mockito.when(userService.getUserTypes(Mockito.anyLong())).thenReturn(userTypesDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/user-types")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("isEmployee").value(true))
				.andExpect(jsonPath("isEmployer").value(false));

		Mockito.verify(userService).getUserTypes(Mockito.eq(1L));
	}

	@Test
	void getUserTypes_ReturnsNotFound() throws Exception {
		Mockito.when(userService.getUserTypes(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/user-types")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).getUserTypes(Mockito.eq(1L));
	}

	@Test
	void getUserInfo_Success() throws Exception {
		GetUserInfoDTO userInfoDTO = GetUserInfoDTO.builder()
				.firstName("John")
				.lastName("Doe")
				.email("john.doe@example.com")
				.phone("+123456789")
				.description("Test user")
				.build();

		Mockito.when(userService.getUserInfo(Mockito.anyLong())).thenReturn(userInfoDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/user-info")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName").value("John"))
				.andExpect(jsonPath("$.lastName").value("Doe"))
				.andExpect(jsonPath("$.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.phone").value("+123456789"))
				.andExpect(jsonPath("$.description").value("Test user"));

		Mockito.verify(userService).getUserInfo(Mockito.eq(1L));
	}

	@Test
	void getUserInfo_Returns_NotFound() throws Exception {
		Mockito.when(userService.getUserInfo(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/user-info")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).getUserInfo(Mockito.eq(1L));
	}

	@Test
	void getLoginHistory_Success() throws Exception {
		List<GetLoginHistoryDTO> loginHistory = List.of(
				GetLoginHistoryDTO.builder().loginDate(LocalDateTime.of(2023, 12, 1, 10, 0)).build(),
				GetLoginHistoryDTO.builder().loginDate(LocalDateTime.of(2023, 12, 2, 11, 0)).build()
		);

		Mockito.when(userService.getLoginHistoryByUserId(Mockito.anyLong())).thenReturn(loginHistory);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/login-history")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].loginDate").value("2023-12-01T10:00:00"))
				.andExpect(jsonPath("$[1].loginDate").value("2023-12-02T11:00:00"));

		Mockito.verify(userService).getLoginHistoryByUserId(Mockito.eq(1L));
	}

	@Test
	void getLoginHistory_Returns_NotFound() throws Exception {
		Mockito.when(userService.getLoginHistoryByUserId(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/login-history")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).getLoginHistoryByUserId(Mockito.eq(1L));
	}

	@Test
	void addStatusForUser_Success() throws Exception {
		Mockito.doNothing().when(userService).addStatusForUserById(Mockito.anyLong(), Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.post("/users/1/add-status/10")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(userService).addStatusForUserById(Mockito.eq(1L), Mockito.eq(10L));
	}

	@Test
	void addStatusForUser_Returns_NotFound() throws Exception {
		Mockito.doThrow(NoSuchElementException.class).when(userService).addStatusForUserById(Mockito.anyLong(), Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.post("/users/1/add-status/10")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).addStatusForUserById(Mockito.eq(1L), Mockito.eq(10L));
	}

	@Test
	void getUserStatuses_Success() throws Exception {
		List<GetUserStatusesDTO> userStatuses = List.of(
				GetUserStatusesDTO.builder().name("Active").build(),
				GetUserStatusesDTO.builder().name("Pending").build()
		);

		Mockito.when(userService.getUserStatusesById(Mockito.anyLong())).thenReturn(userStatuses);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/statuses")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name").value("Active"))
				.andExpect(jsonPath("$[1].name").value("Pending"));

		Mockito.verify(userService).getUserStatusesById(Mockito.eq(1L));
	}

	@Test
	void getUserStatuses_Returns_NotFound() throws Exception {
		Mockito.when(userService.getUserStatusesById(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/statuses")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).getUserStatusesById(Mockito.eq(1L));
	}

	@Test
	void getSupportTicketByUserId_Success() throws Exception {
		List<GetSupportTicketDTO> tickets = List.of(
				GetSupportTicketDTO.builder()
						.id(1L)
						.name("Issue 1")
						.description("Problem with application")
						.ticketStatus("Open")
						.user(GetSupportTicketDTO.UserDTO.builder()
								.id(1L)
								.firstName("John")
								.lastName("Doe")
								.email("john.doe@example.com")
								.phone("1234567890")
								.statuses(List.of("Active"))
								.build())
						.build()
		);

		Mockito.when(userService.getAllSupportTicketsByUserId(Mockito.anyLong())).thenReturn(tickets);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/ticket")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Issue 1"))
				.andExpect(jsonPath("$[0].description").value("Problem with application"))
				.andExpect(jsonPath("$[0].ticketStatus").value("Open"))
				.andExpect(jsonPath("$[0].user.id").value(1))
				.andExpect(jsonPath("$[0].user.firstName").value("John"))
				.andExpect(jsonPath("$[0].user.lastName").value("Doe"))
				.andExpect(jsonPath("$[0].user.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$[0].user.phone").value("1234567890"))
				.andExpect(jsonPath("$[0].user.statuses[0]").value("Active"));

		Mockito.verify(userService).getAllSupportTicketsByUserId(Mockito.eq(1L));
	}

	@Test
	void getSupportTicketByUserId_Returns_NotFound() throws Exception {
		Mockito.when(userService.getAllSupportTicketsByUserId(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.get("/users/1/ticket")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).getAllSupportTicketsByUserId(Mockito.eq(1L));
	}

	@Test
	void addSupportTicket_Success() throws Exception {
		PostSupportTicketDTO postTicketDTO = PostSupportTicketDTO.builder()
				.name("Issue 1")
				.description("Problem with application")
				.build();

		GetSupportTicketDTO addedTicket = GetSupportTicketDTO.builder()
				.id(1L)
				.name("Issue 1")
				.description("Problem with application")
				.ticketStatus("Open")
				.user(GetSupportTicketDTO.UserDTO.builder()
						.id(1L)
						.firstName("John")
						.lastName("Doe")
						.email("john.doe@example.com")
						.phone("1234567890")
						.statuses(List.of("Active"))
						.build())
				.build();

		Mockito.when(userService.addSupportTicket(Mockito.anyLong(), Mockito.any(PostSupportTicketDTO.class)))
				.thenReturn(addedTicket);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/1/ticket")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(postTicketDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Issue 1"))
				.andExpect(jsonPath("$.description").value("Problem with application"))
				.andExpect(jsonPath("$.ticketStatus").value("Open"))
				.andExpect(jsonPath("$.user.id").value(1))
				.andExpect(jsonPath("$.user.firstName").value("John"))
				.andExpect(jsonPath("$.user.lastName").value("Doe"))
				.andExpect(jsonPath("$.user.email").value("john.doe@example.com"))
				.andExpect(jsonPath("$.user.phone").value("1234567890"))
				.andExpect(jsonPath("$.user.statuses[0]").value("Active"));

		Mockito.verify(userService).addSupportTicket(Mockito.eq(1L), Mockito.eq(postTicketDTO));
	}

	@Test
	void addSupportTicket_Returns_NotFound() throws Exception {
		PostSupportTicketDTO postTicketDTO = PostSupportTicketDTO.builder()
				.name("Issue 1")
				.description("Problem with application")
				.build();

		Mockito.when(userService.addSupportTicket(Mockito.anyLong(), Mockito.any(PostSupportTicketDTO.class)))
				.thenThrow(NoSuchElementException.class);

		mockMvc.perform(MockMvcRequestBuilders.post("/users/1/ticket")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(postTicketDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(userService).addSupportTicket(Mockito.eq(1L), Mockito.eq(postTicketDTO));
	}





}
