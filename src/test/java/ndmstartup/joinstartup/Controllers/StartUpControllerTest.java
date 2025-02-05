package ndmstartup.joinstartup.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationCvService;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import ndmstartup.joinstartup.Services.Interfaces.StartUpService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StartUpController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StartUpControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StartUpService startUpService;

	@MockBean
	private ApplicationService applicationService;

	@MockBean
	private ApplicationCvService applicationCvService;

	@MockBean
	private JwtFilter jwtFilter;

	@Test
	void getAllStartUps_Success() throws Exception {
		List<GetStartUpDTO> mockStartUps = Collections.singletonList(
				new GetStartUpDTO(
						1L,
						"Test StartUp",
						"Test Company",
						"Description",
						"Requirements",
						"Location",
						true
				)
		);

		Mockito.when(startUpService.getAllStartUps()).thenReturn(mockStartUps);

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Test StartUp"))
				.andExpect(jsonPath("$[0].companyName").value("Test Company"))
				.andExpect(jsonPath("$[0].description").value("Description"))
				.andExpect(jsonPath("$[0].requirements").value("Requirements"))
				.andExpect(jsonPath("$[0].location").value("Location"))
				.andExpect(jsonPath("$[0].paid").value(true));

		Mockito.verify(startUpService, Mockito.times(1)).getAllStartUps();
	}

	@Test
	void getAllStartUps_NotFound() throws Exception {
		Mockito.when(startUpService.getAllStartUps()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(startUpService, Mockito.times(1)).getAllStartUps();
	}

	@Test
	void searchStartUp_Success() throws Exception {
		List<GetStartUpDTO> mockStartUps = Collections.singletonList(
				new GetStartUpDTO(
						1L,
						"Test StartUp",
						"Test Company",
						"Description",
						"Requirements",
						"Location",
						true
				)
		);

		Mockito.when(startUpService.searchStartUpByCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(mockStartUps);

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/search")
						.param("startupId", "1")
						.param("companyName", "Test Company")
						.param("location", "Location")
						.param("isPaid", "true")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Test StartUp"))
				.andExpect(jsonPath("$[0].companyName").value("Test Company"))
				.andExpect(jsonPath("$[0].description").value("Description"))
				.andExpect(jsonPath("$[0].requirements").value("Requirements"))
				.andExpect(jsonPath("$[0].location").value("Location"))
				.andExpect(jsonPath("$[0].paid").value(true));

		Mockito.verify(startUpService, Mockito.times(1))
				.searchStartUpByCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
	}

	@Test
	void searchStartUp_NotFound() throws Exception {
		Mockito.when(startUpService.searchStartUpByCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/search")
						.param("startupId", "1")
						.param("companyName", "Nonexistent Company")
						.param("location", "Unknown Location")
						.param("isPaid", "true")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(startUpService, Mockito.times(1))
				.searchStartUpByCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());
	}


	@Test
	void getApplications_Success() throws Exception {
		GetApplicationDTO getApplicationDTO = GetApplicationDTO.builder()
				.id(1L)
				.applicationDate(LocalDateTime.of(2025, 2, 4, 17, 24, 0))
				.applicationStatus(new GetApplicationDTO.ApplicationStatusDTO("Pending"))
				.build();
		List<GetApplicationDTO> applications = Collections.singletonList(getApplicationDTO);

		Mockito.when(applicationService.getApplicationsByStartUpAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong()))
				.thenReturn(applications);

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/1/applications")
						.param("applicationStatus", "Pending")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].applicationDate").value("2025-02-04T17:24:00"))
				.andExpect(jsonPath("$[0].applicationStatus.applicationStatusName").value("Pending"));

		Mockito.verify(applicationService, Mockito.times(1))
				.getApplicationsByStartUpAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong());
	}

	@Test
	void getApplications_NotFound() throws Exception {
		Mockito.when(applicationService.getApplicationsByStartUpAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong()))
				.thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/1/applications")
						.param("applicationStatus", "Pending")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(applicationService, Mockito.times(1))
				.getApplicationsByStartUpAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong());
	}

	@Test
	void getAllEmployeesByStartUpId_Success() throws Exception {
		GetStartUpIdEmployeeDTO.EmployeeDTO employee1 = GetStartUpIdEmployeeDTO.EmployeeDTO.builder()
				.employeeId(1L)
				.user(GetStartUpIdEmployeeDTO.UserDTO.builder()
						.firstName("Employee1")
						.build())
				.build();
		GetStartUpIdEmployeeDTO.EmployeeDTO employee2 = GetStartUpIdEmployeeDTO.EmployeeDTO.builder()
				.employeeId(1L)
				.user(GetStartUpIdEmployeeDTO.UserDTO.builder()
						.firstName("Employee2")
						.build())
				.build();
		GetStartUpIdEmployeeDTO getStartUpIdEmployeeDTO = new GetStartUpIdEmployeeDTO(1L, List.of(employee1, employee2));

		Mockito.when(startUpService.getEmployeesByStartUpId(Mockito.anyLong())).thenReturn(getStartUpIdEmployeeDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/1/employees")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.startUpId").value(1))
				.andExpect(jsonPath("$.employeeDTOList[0].user.firstName").value("Employee1"))
				.andExpect(jsonPath("$.employeeDTOList[1].user.firstName").value("Employee2"));

		Mockito.verify(startUpService, Mockito.times(1)).getEmployeesByStartUpId(Mockito.anyLong());
	}

	@Test
	void getAllEmployeesByStartUpId_NotFound() throws Exception {
		Mockito.when(startUpService.getEmployeesByStartUpId(Mockito.anyLong()))
				.thenThrow(new NoSuchElementException("No employees found for the given StartUp ID"));

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/999/employees")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(startUpService, Mockito.times(1)).getEmployeesByStartUpId(Mockito.anyLong());
	}

	@Test
	void addStartUp_Success() throws Exception {
		PostStartUpDTO postStartUpDTO = new PostStartUpDTO(
				"New StartUp",
				"New Company",
				"Innovative startup in AI",
				"Strong Java skills",
				"New York",
				true
		);

		Mockito.doNothing().when(startUpService).addStartUp(Mockito.any(PostStartUpDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.post("/startUp/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(postStartUpDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(startUpService, Mockito.times(1)).addStartUp(Mockito.any(PostStartUpDTO.class));
	}

	@Test
	void updateStartUp_Success() throws Exception {
		PostStartUpDTO postStartUpDTO = new PostStartUpDTO(
				"Updated StartUp",
				"Updated Company",
				"Updated Description",
				"Updated Requirements",
				"Updated Location",
				true
		);

		Mockito.doNothing().when(startUpService).updateStartUp(Mockito.anyLong(), Mockito.any(PostStartUpDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.put("/startUp/1/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(postStartUpDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(startUpService, Mockito.times(1)).updateStartUp(Mockito.eq(1L), Mockito.any(PostStartUpDTO.class));
	}

	@Test
	void updateStartUp_NotFound() throws Exception {
		PostStartUpDTO postStartUpDTO = new PostStartUpDTO(
				"Updated StartUp",
				"Updated Company",
				"Updated Description",
				"Updated Requirements",
				"Updated Location",
				true
		);

		Mockito.doThrow(new NoSuchElementException("StartUp not found")).when(startUpService)
				.updateStartUp(Mockito.anyLong(), Mockito.any(PostStartUpDTO.class));

		mockMvc.perform(MockMvcRequestBuilders.put("/startUp/999/update")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(postStartUpDTO))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(startUpService, Mockito.times(1)).updateStartUp(Mockito.eq(999L), Mockito.any(PostStartUpDTO.class));
	}

	@Test
	void deleteStartUp_Success() throws Exception {
		Mockito.doNothing().when(startUpService).deleteStartUp(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/startUp/1/delete")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(startUpService, Mockito.times(1)).deleteStartUp(Mockito.eq(1L));
	}

	@Test
	void deleteStartUp_NotFound() throws Exception {
		Mockito.doThrow(new NoSuchElementException("StartUp not found")).when(startUpService)
				.deleteStartUp(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/startUp/999/delete")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(startUpService, Mockito.times(1)).deleteStartUp(Mockito.eq(999L));
	}

	@Test
	void getStartUpsByProgressStatus_Success() throws Exception {
		List<GetStartUpDTO> mockStartUps = Collections.singletonList(
				new GetStartUpDTO(
						1L,
						"Test StartUp",
						"Test Company",
						"Description",
						"Requirements",
						"Location",
						true
				)
		);

		Mockito.when(startUpService.getStartUpsByProgressStatus(Mockito.anyString()))
				.thenReturn(mockStartUps);

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/by-progress-status")
						.param("progressStatusName", "Active")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Test StartUp"))
				.andExpect(jsonPath("$[0].companyName").value("Test Company"))
				.andExpect(jsonPath("$[0].description").value("Description"))
				.andExpect(jsonPath("$[0].requirements").value("Requirements"))
				.andExpect(jsonPath("$[0].location").value("Location"))
				.andExpect(jsonPath("$[0].paid").value(true));

		Mockito.verify(startUpService, Mockito.times(1)).getStartUpsByProgressStatus(Mockito.eq("Active"));
	}

	@Test
	void getStartUpsByProgressStatus_NotFound() throws Exception {
		Mockito.when(startUpService.getStartUpsByProgressStatus(Mockito.anyString()))
				.thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/by-progress-status")
						.param("progressStatusName", "NonexistentStatus")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(startUpService, Mockito.times(1)).getStartUpsByProgressStatus(Mockito.eq("NonexistentStatus"));
	}

	@Test
	void updateStartUpStatus_Success() throws Exception {
		Mockito.doNothing().when(startUpService).updateStartUpStatus(Mockito.anyLong(), Mockito.anyString());

		mockMvc.perform(MockMvcRequestBuilders.put("/startUp/1/status")
						.param("progressStatusName", "Active")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(startUpService, Mockito.times(1))
				.updateStartUpStatus(Mockito.eq(1L), Mockito.eq("Active"));
	}

	@Test
	void updateStartUpStatus_NotFound() throws Exception {
		Mockito.doThrow(new NoSuchElementException("StartUp not found"))
				.when(startUpService).updateStartUpStatus(Mockito.anyLong(), Mockito.anyString());

		mockMvc.perform(MockMvcRequestBuilders.put("/startUp/999/status")
						.param("progressStatusName", "Inactive")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(startUpService, Mockito.times(1))
				.updateStartUpStatus(Mockito.eq(999L), Mockito.eq("Inactive"));
	}

	@Test
	void deleteStartUpSkills_Success() throws Exception {
		Mockito.doNothing().when(startUpService).deleteStartUpSkills(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/startUp/1/skills")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(startUpService, Mockito.times(1)).deleteStartUpSkills(Mockito.eq(1L));
	}

	@Test
	void deleteStartUpSkills_NotFound() throws Exception {
		Mockito.doThrow(new NoSuchElementException("StartUp not found"))
				.when(startUpService).deleteStartUpSkills(Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/startUp/999/skills")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(startUpService, Mockito.times(1)).deleteStartUpSkills(Mockito.eq(999L));
	}

	@Test
	void getAllStartUpProgressStatuses_Success() throws Exception {
		List<GetStartUpStatusDTO> mockStatuses = List.of(
				new GetStartUpStatusDTO("Done", LocalDate.of(2025, 2, 4)),
				new GetStartUpStatusDTO("In Progress", LocalDate.of(2025, 2, 4))
		);

		Mockito.when(startUpService.getAllStartUpProgressStatuses(Mockito.anyLong()))
				.thenReturn(mockStatuses);

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/1/progress-statuses")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].statusName").value("Done"))
				.andExpect(jsonPath("$[0].date").value("2025-02-04"))
				.andExpect(jsonPath("$[1].statusName").value("In Progress"))
				.andExpect(jsonPath("$[1].date").value("2025-02-04"));

		Mockito.verify(startUpService, Mockito.times(1))
				.getAllStartUpProgressStatuses(Mockito.eq(1L));
	}

	@Test
	void getAllStartUpProgressStatuses_NotFound() throws Exception {
		Mockito.when(startUpService.getAllStartUpProgressStatuses(Mockito.anyLong()))
				.thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/startUp/999/progress-statuses")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(startUpService, Mockito.times(1))
				.getAllStartUpProgressStatuses(Mockito.eq(999L));
	}

	@Test
	void updateApplicationStatus_Success() throws Exception {
		Mockito.doNothing().when(applicationCvService).updateApplicationStatus(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.put("/startUp/1/application/10/status")
						.param("applicationStatusName", "Accepted")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		Mockito.verify(applicationCvService, Mockito.times(1))
				.updateApplicationStatus(Mockito.eq(10L), Mockito.eq("Accepted"), Mockito.eq(1L));
	}

	@Test
	void updateApplicationStatus_NotFound() throws Exception {
		Mockito.doThrow(new NoSuchElementException("Application not found"))
				.when(applicationCvService).updateApplicationStatus(Mockito.anyLong(), Mockito.anyString(), Mockito.anyLong());

		mockMvc.perform(MockMvcRequestBuilders.put("/startUp/999/application/100/status")
						.param("applicationStatusName", "Rejected")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(applicationCvService, Mockito.times(1))
				.updateApplicationStatus(Mockito.eq(100L), Mockito.eq("Rejected"), Mockito.eq(999L));
	}


}
