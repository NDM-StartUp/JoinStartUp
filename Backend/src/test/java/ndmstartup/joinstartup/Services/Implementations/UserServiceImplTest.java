package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Exceptions.StatusAlreadyExistsConflictException;
import ndmstartup.joinstartup.Exceptions.UserChangeTypeConflictException;
import ndmstartup.joinstartup.Mappers.SupportTicketMapper;
import ndmstartup.joinstartup.Mappers.UserMapper;
import ndmstartup.joinstartup.Models.*;
import ndmstartup.joinstartup.Repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private EmployerRepository employerRepository;
	@Mock
	private LoginHistoryRepository loginHistoryRepository;
	@Mock
	private StatusRepository statusRepository;
	@Mock
	private UserMapper userMapper;
	@Mock
	private SupportTicketRepository supportTicketRepository;
	@Mock
	private SupportTicketMapper supportTicketMapper;
	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void addUser_AsEmployee() {
		PostUserDTO postUserDTO = new PostUserDTO();
		User user = new User();
		when(userMapper.DTOToEntity(postUserDTO)).thenReturn(user);

		userService.addUser(postUserDTO, true);

		verify(userMapper, times(1)).DTOToEntity(postUserDTO);
		verify(userRepository, times(1)).save(user);
		verify(employeeRepository, times(1)).save(any(Employee.class));
		verify(employerRepository, never()).save(any(Employer.class));
	}

	@Test
	void addUser_AsEmployer() {
		PostUserDTO postUserDTO = new PostUserDTO();
		User user = new User();
		when(userMapper.DTOToEntity(postUserDTO)).thenReturn(user);

		userService.addUser(postUserDTO, false);

		verify(userMapper, times(1)).DTOToEntity(postUserDTO);
		verify(userRepository, times(1)).save(user);
		verify(employerRepository, times(1)).save(any(Employer.class));
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	void addUserRole_AddEmployerRole_Success() {
		Long userId = 1L;
		User user = new User();
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(employerRepository.existsEmployerById(userId)).thenReturn(false);

		userService.addUserRole(userId, true);

		verify(userRepository, times(1)).findById(userId);
		verify(employerRepository, times(1)).existsEmployerById(userId);
		verify(employerRepository, times(1)).save(any(Employer.class));
		verify(employeeRepository, never()).save(any(Employee.class));
	}

	@Test
	void addUserRole_AddEmployeeRole_Success() {
		Long userId = 1L;
		User user = new User();
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(employeeRepository.existsEmployeeById(userId)).thenReturn(false);

		userService.addUserRole(userId, false);

		verify(userRepository, times(1)).findById(userId);
		verify(employeeRepository, times(1)).existsEmployeeById(userId);
		verify(employeeRepository, times(1)).save(any(Employee.class));
		verify(employerRepository, never()).save(any(Employer.class));
	}

	@Test
	void addUserRole_UserNotFound_ThrowsNoSuchElementException() {
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> userService.addUserRole(userId, true)
		);

		assertEquals("User not found with id " + userId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(employerRepository, never()).existsEmployerById(anyLong());
		verify(employeeRepository, never()).existsEmployeeById(anyLong());
	}

	@Test
	void addUserRole_UserAlreadyEmployer_ThrowsUserChangeTypeConflictException() {
		Long userId = 1L;
		User user = new User();
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(employerRepository.existsEmployerById(userId)).thenReturn(true);

		UserChangeTypeConflictException exception = assertThrows(UserChangeTypeConflictException.class,
				() -> userService.addUserRole(userId, true));

		assertEquals("User of id - " + userId + ", is already an Employer.", exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(employerRepository, times(1)).existsEmployerById(userId);
		verify(employerRepository, never()).save(any(Employer.class));
	}

	@Test
	void updateUserData_Success() {
		Long userId = 1L;
		User existingUser = new User();
		existingUser.setId(userId);

		PostUserDTO postUserDTO = new PostUserDTO();
		postUserDTO.setFirstName("John");
		postUserDTO.setLastName("Doe");
		postUserDTO.setEmail("johndoe@example.com");
		postUserDTO.setPhone("123456789");
		postUserDTO.setDescription("Description");

		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		userService.updateUserData(userId, postUserDTO);

		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, times(1)).save(existingUser);

		assertEquals("John", existingUser.getFirstName());
		assertEquals("Doe", existingUser.getLastName());
		assertEquals("johndoe@example.com", existingUser.getEmail());
		assertEquals("123456789", existingUser.getPhone());
		assertEquals("Description", existingUser.getDescription());
	}

	@Test
	void updateUserData_UserNotFound_ThrowsNoSuchElementException() {
		Long userId = 1L;
		PostUserDTO postUserDTO = new PostUserDTO();
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> userService.updateUserData(userId, postUserDTO));

		assertEquals("User not found with id " + userId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, never()).save(any());
	}

	@Test
	void deleteUser_Success() {
		Long userId = 1L;
		User existingUser = new User();
		existingUser.setId(userId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

		userService.deleteUser(userId);

		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, times(1)).delete(existingUser);
	}

	@Test
	void deleteUser_UserNotFound_ThrowsNoSuchElementException() {
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> userService.deleteUser(userId));

		assertEquals("User not found with id " + userId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(userRepository, never()).delete(any());
	}

	@Test
	void getUserTypes_UserIsOnlyEmployee() {
		Long userId = 1L;
		when(employeeRepository.existsEmployeeById(userId)).thenReturn(true);
		when(employerRepository.existsEmployerById(userId)).thenReturn(false);

		GetUserTypesDTO userTypes = userService.getUserTypes(userId);

		assertTrue(userTypes.isEmployee());
		assertFalse(userTypes.isEmployer());
		verify(employeeRepository, times(1)).existsEmployeeById(userId);
		verify(employerRepository, times(1)).existsEmployerById(userId);
	}

	@Test
	void getUserTypes_UserIsOnlyEmployer() {
		Long userId = 1L;
		when(employeeRepository.existsEmployeeById(userId)).thenReturn(false);
		when(employerRepository.existsEmployerById(userId)).thenReturn(true);

		GetUserTypesDTO userTypes = userService.getUserTypes(userId);

		assertFalse(userTypes.isEmployee());
		assertTrue(userTypes.isEmployer());
		verify(employeeRepository, times(1)).existsEmployeeById(userId);
		verify(employerRepository, times(1)).existsEmployerById(userId);
	}

	@Test
	void getUserTypes_UserIsBothEmployeeAndEmployer() {
		Long userId = 1L;
		when(employeeRepository.existsEmployeeById(userId)).thenReturn(true);
		when(employerRepository.existsEmployerById(userId)).thenReturn(true);

		GetUserTypesDTO userTypes = userService.getUserTypes(userId);

		assertTrue(userTypes.isEmployee());
		assertTrue(userTypes.isEmployer());
		verify(employeeRepository, times(1)).existsEmployeeById(userId);
		verify(employerRepository, times(1)).existsEmployerById(userId);
	}

	@Test
	void getUserTypes_UserIsNeitherEmployeeNorEmployer() {
		Long userId = 1L;
		when(employeeRepository.existsEmployeeById(userId)).thenReturn(false);
		when(employerRepository.existsEmployerById(userId)).thenReturn(false);

		GetUserTypesDTO userTypes = userService.getUserTypes(userId);

		assertFalse(userTypes.isEmployee());
		assertFalse(userTypes.isEmployer());
		verify(employeeRepository, times(1)).existsEmployeeById(userId);
		verify(employerRepository, times(1)).existsEmployerById(userId);
	}

	@Test
	void getUserInfo_Success() {
		Long userId = 1L;
		User user = new User();
		user.setId(userId);
		GetUserInfoDTO userInfoDTO = new GetUserInfoDTO();

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(userMapper.EntityToInfoDTO(user)).thenReturn(userInfoDTO);

		GetUserInfoDTO result = userService.getUserInfo(userId);

		verify(userRepository, times(1)).findById(userId);
		verify(userMapper, times(1)).EntityToInfoDTO(user);
		assertNotNull(result);
		assertEquals(userInfoDTO, result);
	}

	@Test
	void getUserInfo_UserNotFound_ThrowsNoSuchElementException() {
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> userService.getUserInfo(userId));

		assertEquals("User not found with id " + userId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(userMapper, never()).EntityToInfoDTO(any());
	}

	private LoginHistory createLoginHistory(Long id, LocalDateTime date, User user) {
		LoginHistory loginHistory = new LoginHistory();
		loginHistory.setId(id);
		loginHistory.setDate(date);
		loginHistory.setUser(user);

		return loginHistory;
	}

	@Test
	void getLoginHistoryByUserId_Success() {
		Long userId = 1L;
		User user = new User();
		user.setId(userId);

		List<LoginHistory> loginHistoryList = new ArrayList<>();
		loginHistoryList.add(createLoginHistory(1L, LocalDateTime.of(2024, 1, 1, 12, 0), user));
		loginHistoryList.add(createLoginHistory(2L, LocalDateTime.of(2024, 1, 2, 14, 0), user));

		when(loginHistoryRepository.findLoginHistoriesByUserId(userId)).thenReturn(loginHistoryList);

		List<GetLoginHistoryDTO> result = userService.getLoginHistoryByUserId(userId);

		verify(loginHistoryRepository, times(1)).findLoginHistoriesByUserId(userId);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(LocalDateTime.of(2024, 1, 1, 12, 0), result.get(0).getLoginDate());
		assertEquals(LocalDateTime.of(2024, 1, 2, 14, 0), result.get(1).getLoginDate());
	}

	@Test
	void getLoginHistoryByUserId_NoHistoryFound_ReturnsEmptyList() {
		Long userId = 1L;
		when(loginHistoryRepository.findLoginHistoriesByUserId(userId)).thenReturn(new ArrayList<>());

		List<GetLoginHistoryDTO> result = userService.getLoginHistoryByUserId(userId);

		verify(loginHistoryRepository, times(1)).findLoginHistoriesByUserId(userId);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void addStatusForUserById_Success() {
		Long userId = 1L;
		Long statusId = 2L;
		User user = new User();
		user.setId(userId);
		user.setStatuses(new ArrayList<>());

		Status status = new Status();
		status.setId(statusId);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(statusRepository.findById(statusId)).thenReturn(Optional.of(status));

		userService.addStatusForUserById(userId, statusId);

		verify(userRepository, times(1)).findById(userId);
		verify(statusRepository, times(1)).findById(statusId);
		verify(userRepository, times(1)).save(user);

		assertTrue(user.getStatuses().contains(status));
	}

	@Test
	void addStatusForUserById_UserNotFound_ThrowsNoSuchElementException() {
		Long userId = 1L;
		Long statusId = 2L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> userService.addStatusForUserById(userId, statusId));

		assertEquals("User not found with id " + userId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(statusRepository, never()).findById(anyLong());
		verify(userRepository, never()).save(any());
	}

	@Test
	void addStatusForUserById_StatusNotFound_ThrowsNoSuchElementException() {
		Long userId = 1L;
		Long statusId = 2L;
		User user = new User();
		user.setId(userId);
		user.setStatuses(new ArrayList<>());

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(statusRepository.findById(statusId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> userService.addStatusForUserById(userId, statusId));

		assertEquals("Status not found with id" + statusId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(statusRepository, times(1)).findById(statusId);
		verify(userRepository, never()).save(any());
	}

	@Test
	void addStatusForUserById_StatusAlreadyExists_ThrowsStatusAlreadyExistsConflictException() {
		Long userId = 1L;
		Long statusId = 2L;
		User user = new User();
		user.setId(userId);

		Status status = new Status();
		status.setId(statusId);

		user.setStatuses(new ArrayList<>());
		user.getStatuses().add(status);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(statusRepository.findById(statusId)).thenReturn(Optional.of(status));

		StatusAlreadyExistsConflictException exception = assertThrows(StatusAlreadyExistsConflictException.class,
				() -> userService.addStatusForUserById(userId, statusId));

		assertEquals("There is already status with id - " + statusId + ", assigned to user with id - " + userId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
		verify(statusRepository, times(1)).findById(statusId);
		verify(userRepository, never()).save(any());
	}

	private Status createStatus(Long id, String name) {
		Status status = new Status();
		status.setId(id);
		status.setName(name);

		return status;
	}

	@Test
	void getUserStatusesById_Success() {
		Long userId = 1L;
		User user = new User();
		user.setId(userId);

		List<Status> statuses = new ArrayList<>();
		statuses.add(createStatus(1L, "Active"));
		statuses.add(createStatus(2L, "Inactive"));
		user.setStatuses(statuses);

		when(userRepository.findById(userId)).thenReturn(Optional.of(user));

		List<GetUserStatusesDTO> result = userService.getUserStatusesById(userId);

		verify(userRepository, times(1)).findById(userId);
		assertNotNull(result);
		assertEquals(2, result.size());
		List<String> statusNames = result.stream().map(GetUserStatusesDTO::getName).toList();
		assertTrue(statusNames.contains("Active"));
		assertTrue(statusNames.contains("Inactive"));
	}

	@Test
	void getUserStatusesById_UserNotFound_ThrowsNoSuchElementException() {
		Long userId = 1L;
		when(userRepository.findById(userId)).thenReturn(Optional.empty());

		NoSuchElementException exception = assertThrows(NoSuchElementException.class,
				() -> userService.getUserStatusesById(userId));

		assertEquals("User not found with id " + userId, exception.getMessage());
		verify(userRepository, times(1)).findById(userId);
	}

	private SupportTicket createSupportTicket(Long id, String name) {
		SupportTicket supportTicket = new SupportTicket();
		supportTicket.setId(id);
		supportTicket.setName(name);

		return supportTicket;
	}

	@Test
	void getAllSupportTicketsByUserId_Success() {
		long userId = 1L;

		List<SupportTicket> supportTickets = new ArrayList<>();
		supportTickets.add(createSupportTicket(1L, "Ticket 1"));
		supportTickets.add(createSupportTicket(2L, "Ticket 2"));

		List<GetSupportTicketDTO> expectedDTOs = new ArrayList<>();
		expectedDTOs.add(GetSupportTicketDTO.builder()
				.id(1L)
				.name("Ticket 1")
				.description("Desc 1")
				.ticketStatus("Open")
				.user(GetSupportTicketDTO.UserDTO.builder()
						.id(1L)
						.firstName("John")
						.lastName("Doe")
						.email("john.doe@example.com")
						.phone("123456789")
						.statuses(List.of("Active", "Premium"))
						.build())
				.build());

		expectedDTOs.add(GetSupportTicketDTO.builder()
				.id(2L)
				.name("Ticket 2")
				.description("Desc 2")
				.ticketStatus("Closed")
				.user(GetSupportTicketDTO.UserDTO.builder()
						.id(2L)
						.firstName("Jane")
						.lastName("Smith")
						.email("jane.smith@example.com")
						.phone("987654321")
						.statuses(List.of("Basic"))
						.build())
				.build());

		when(supportTicketRepository.findAllByUserId(userId)).thenReturn(supportTickets);
		when(supportTicketMapper.entityToDTO(supportTickets.get(0))).thenReturn(expectedDTOs.get(0));
		when(supportTicketMapper.entityToDTO(supportTickets.get(1))).thenReturn(expectedDTOs.get(1));

		List<GetSupportTicketDTO> result = userService.getAllSupportTicketsByUserId(userId);

		verify(supportTicketRepository, times(1)).findAllByUserId(userId);
		verify(supportTicketMapper, times(2)).entityToDTO(any(SupportTicket.class));
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(expectedDTOs, result);
	}

	@Test
	void getAllSupportTicketsByUserId_NoTicketsFound_ReturnsEmptyList() {
		long userId = 1L;
		when(supportTicketRepository.findAllByUserId(userId)).thenReturn(new ArrayList<>());

		List<GetSupportTicketDTO> result = userService.getAllSupportTicketsByUserId(userId);

		verify(supportTicketRepository, times(1)).findAllByUserId(userId);
		verify(supportTicketMapper, never()).entityToDTO(any());
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void addSupportTicket_Success() {
		Long userId = 1L;
		PostSupportTicketDTO postSupportTicketDTO = new PostSupportTicketDTO();
		postSupportTicketDTO.setName("Issue with login");
		postSupportTicketDTO.setDescription("Unable to log in due to server error.");

		SupportTicket supportTicket = new SupportTicket();
		supportTicket.setId(1L);
		supportTicket.setName("Issue with login");
		supportTicket.setDescription("Unable to log in due to server error.");

		GetSupportTicketDTO expectedDTO = GetSupportTicketDTO.builder()
				.id(1L)
				.name("Issue with login")
				.description("Unable to log in due to server error.")
				.ticketStatus("Open")
				.build();

		when(supportTicketMapper.DTOToEntity(postSupportTicketDTO, userId)).thenReturn(supportTicket);
		when(supportTicketRepository.save(supportTicket)).thenReturn(supportTicket);
		when(supportTicketMapper.entityToDTO(supportTicket)).thenReturn(expectedDTO);

		GetSupportTicketDTO result = userService.addSupportTicket(userId, postSupportTicketDTO);

		verify(supportTicketMapper, times(1)).DTOToEntity(postSupportTicketDTO, userId);
		verify(supportTicketRepository, times(1)).save(supportTicket);
		verify(supportTicketMapper, times(1)).entityToDTO(supportTicket);

		assertNotNull(result);
		assertEquals(expectedDTO, result);
	}
}
