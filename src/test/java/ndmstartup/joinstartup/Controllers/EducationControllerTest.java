package ndmstartup.joinstartup.Controllers;

import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Services.Interfaces.DegreeTypeService;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeEducationService;
import ndmstartup.joinstartup.Services.Interfaces.MajorService;
import ndmstartup.joinstartup.Services.Interfaces.UniversityService;
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

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EducationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EducationControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private UniversityService universityService;

	@MockBean
	private MajorService majorService;

	@MockBean
	private DegreeTypeService degreeTypeService;

	@MockBean
	private EmployeeEducationService employeeEducationService;

	@Test
	void getAllUniversities_Success() throws Exception {
		List<GetUniversityDTO> mockUniversities = List.of(
				new GetUniversityDTO(1L, "Harvard", "USA"),
				new GetUniversityDTO(2L, "Stanford", "USA")
		);

		Mockito.when(universityService.getAllUniversities()).thenReturn(mockUniversities);

		mockMvc.perform(MockMvcRequestBuilders.get("/education/universities")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Harvard"))
				.andExpect(jsonPath("$[0].location").value("USA"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Stanford"))
				.andExpect(jsonPath("$[1].location").value("USA"));

		Mockito.verify(universityService, Mockito.times(1)).getAllUniversities();
	}

	@Test
	void getAllUniversities_NotFound() throws Exception {
		Mockito.when(universityService.getAllUniversities()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/education/universities")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(universityService, Mockito.times(1)).getAllUniversities();
	}

	@Test
	void getAllMajors_Success() throws Exception {
		List<GetMajorDTO> mockMajors = List.of(
				new GetMajorDTO(1L, "Computer Science"),
				new GetMajorDTO(2L, "Mechanical Engineering")
		);

		Mockito.when(majorService.getAllMajors()).thenReturn(mockMajors);

		mockMvc.perform(MockMvcRequestBuilders.get("/education/majors")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Computer Science"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Mechanical Engineering"));

		Mockito.verify(majorService, Mockito.times(1)).getAllMajors();
	}

	@Test
	void getAllMajors_NotFound() throws Exception {
		Mockito.when(majorService.getAllMajors()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/education/majors")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(majorService, Mockito.times(1)).getAllMajors();
	}

	@Test
	void getAllDegreeTypes_Success() throws Exception {
		List<GetDegreeTypeDTO> mockDegreeTypes = List.of(
				new GetDegreeTypeDTO(1L, "Engineer"),
				new GetDegreeTypeDTO(2L, "Master")
		);

		Mockito.when(degreeTypeService.getAllDegreeTypes()).thenReturn(mockDegreeTypes);

		mockMvc.perform(MockMvcRequestBuilders.get("/education/degree-types")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Engineer"))
				.andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].name").value("Master"));

		Mockito.verify(degreeTypeService, Mockito.times(1)).getAllDegreeTypes();
	}

	@Test
	void getAllDegreeTypes_NotFound() throws Exception {
		Mockito.when(degreeTypeService.getAllDegreeTypes()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/education/degree-types")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));

		Mockito.verify(degreeTypeService, Mockito.times(1)).getAllDegreeTypes();
	}

	@Test
	void getUniversityByEducationId_Success() throws Exception {
		GetUniversityDTO mockUniversity = new GetUniversityDTO(1L, "Harvard", "USA");

		Mockito.when(employeeEducationService.getUniversityByEducationId(Mockito.anyLong()))
				.thenReturn(mockUniversity);

		mockMvc.perform(MockMvcRequestBuilders.get("/education/university/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Harvard"))
				.andExpect(jsonPath("$.location").value("USA"));

		Mockito.verify(employeeEducationService, Mockito.times(1))
				.getUniversityByEducationId(Mockito.eq(1L));
	}

	@Test
	void getUniversityByEducationId_NotFound() throws Exception {
		Mockito.when(employeeEducationService.getUniversityByEducationId(Mockito.anyLong()))
				.thenThrow(new NoSuchElementException("University not found"));

		mockMvc.perform(MockMvcRequestBuilders.get("/education/university/999")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(employeeEducationService, Mockito.times(1))
				.getUniversityByEducationId(Mockito.eq(999L));
	}

	@Test
	void getMajorByEducationId_Success() throws Exception {
		GetMajorDTO mockMajor = new GetMajorDTO(1L, "Engineer");

		Mockito.when(employeeEducationService.getMajorByEducationId(Mockito.anyLong()))
				.thenReturn(mockMajor);

		mockMvc.perform(MockMvcRequestBuilders.get("/education/major/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Engineer"));

		Mockito.verify(employeeEducationService, Mockito.times(1))
				.getMajorByEducationId(Mockito.eq(1L));
	}

	@Test
	void getMajorByEducationId_NotFound() throws Exception {
		Mockito.when(employeeEducationService.getMajorByEducationId(Mockito.anyLong()))
				.thenThrow(new NoSuchElementException("Major not found"));

		mockMvc.perform(MockMvcRequestBuilders.get("/education/major/999")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(employeeEducationService, Mockito.times(1))
				.getMajorByEducationId(Mockito.eq(999L));
	}

	@Test
	void getDegreeTypeByEducationId_Success() throws Exception {
		GetDegreeTypeDTO mockDegreeType = new GetDegreeTypeDTO(1L, "Engineer");

		Mockito.when(employeeEducationService.getDegreeTypeByEducationId(Mockito.anyLong()))
				.thenReturn(mockDegreeType);

		mockMvc.perform(MockMvcRequestBuilders.get("/education/degree/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Engineer"));

		Mockito.verify(employeeEducationService, Mockito.times(1))
				.getDegreeTypeByEducationId(Mockito.eq(1L));
	}


	@Test
	void getDegreeTypeByEducationId_NotFound() throws Exception {
		Mockito.when(employeeEducationService.getDegreeTypeByEducationId(Mockito.anyLong()))
				.thenThrow(new NoSuchElementException("Degree type not found"));

		mockMvc.perform(MockMvcRequestBuilders.get("/education/degree/999")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(employeeEducationService, Mockito.times(1))
				.getDegreeTypeByEducationId(Mockito.eq(999L));
	}
}
