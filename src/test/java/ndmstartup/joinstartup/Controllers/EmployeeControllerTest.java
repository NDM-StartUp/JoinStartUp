package ndmstartup.joinstartup.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Models.Position;
import ndmstartup.joinstartup.Security.Filters.JwtFilter;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import ndmstartup.joinstartup.Services.Interfaces.PositionService;
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
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ApplicationService applicationService;

    @MockBean
    private PositionService positionService;

    @Test
    void getExperienceByEmployeeId_Success() throws Exception {
        GetEmployeeExperienceDTO employeeExperienceDTO = new GetEmployeeExperienceDTO(1L, new ArrayList<>());

        Mockito.when(employeeService.getExperienceByEmployeeId(Mockito.anyLong())).thenReturn(employeeExperienceDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/experience")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("employeeId").value(1))
                .andExpect(jsonPath("experience").value(employeeExperienceDTO.getExperience()));

        Mockito.verify(employeeService, Mockito.times(1)).getExperienceByEmployeeId(Mockito.anyLong());
    }

    @Test
    void getExperienceByEmployeeId_Returns_NotFound() throws Exception {
        Mockito.when(employeeService.getExperienceByEmployeeId(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/experience")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(employeeService, Mockito.times(1)).getExperienceByEmployeeId(Mockito.anyLong());
    }

    @Test
    void addExperienceByEmployeeId_Success() throws Exception {
        PostEmployeeExperienceDTO postEmployeeExperienceDTO = new PostEmployeeExperienceDTO(new Position(), "sad", LocalDate.now().minusDays(1), null);

        Mockito.doNothing().when(employeeService).addExperienceByEmployeeId(1L, postEmployeeExperienceDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/experience")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(postEmployeeExperienceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(employeeService).addExperienceByEmployeeId(
                Mockito.eq(1L),
                Mockito.argThat(dto ->
                        dto.getCompanyName().equals(postEmployeeExperienceDTO.getCompanyName()) &&
                                dto.getStartingDate().equals(postEmployeeExperienceDTO.getStartingDate()) &&
                                Objects.equals(dto.getEndingDate(), postEmployeeExperienceDTO.getEndingDate()))
        );
    }

    @Test
    void addExperienceByEmployeeId_Returns_NotFound() throws Exception {
        PostEmployeeExperienceDTO postEmployeeExperienceDTO = new PostEmployeeExperienceDTO(new Position(), "sad", LocalDate.now().minusDays(1), null);

        Mockito.doThrow(NoSuchElementException.class)
                .when(employeeService)
                .addExperienceByEmployeeId(Mockito.any(), Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/experience")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(postEmployeeExperienceDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(employeeService).addExperienceByEmployeeId(
                Mockito.eq(1L),
                Mockito.argThat(dto ->
                        dto.getCompanyName().equals(postEmployeeExperienceDTO.getCompanyName()) &&
                                dto.getStartingDate().equals(postEmployeeExperienceDTO.getStartingDate()) &&
                                Objects.equals(dto.getEndingDate(), postEmployeeExperienceDTO.getEndingDate()))
        );
    }

    @Test
    void getEducationByEmployeeId_Success() throws Exception {
        GetEmployeeEducationDTO employeeEducationDTO = new GetEmployeeEducationDTO(1L, new ArrayList<>());

        Mockito.when(employeeService.getEducationByEmployeeId(Mockito.anyLong())).thenReturn(employeeEducationDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/education")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("employeeId").value(1))
                .andExpect(jsonPath("educationList").value(employeeEducationDTO.getEducationList()));

        Mockito.verify(employeeService, Mockito.times(1)).getEducationByEmployeeId(Mockito.anyLong());
    }

    @Test
    void getEducationByEmployeeId_Returns_NotFound() throws Exception {

        Mockito.when(employeeService.getEducationByEmployeeId(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/education")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(employeeService, Mockito.times(1)).getEducationByEmployeeId(Mockito.anyLong());
    }

    @Test
    void addEducationByEmployeeId_Success() throws Exception {
        PostEmployeeEducationDTO postEmployeeEducationDTO = new PostEmployeeEducationDTO(1L, 1L, 1L, LocalDate.now().minusDays(1), null);

        Mockito.doNothing().when(employeeService).addEducationByEmployeeId(1L, postEmployeeEducationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/education")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(postEmployeeEducationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(employeeService).addEducationByEmployeeId(
                Mockito.eq(1L),
                Mockito.argThat(dto ->
                        dto.getMajorId().equals(postEmployeeEducationDTO.getMajorId()) &&
                                dto.getBeginningDate().equals(postEmployeeEducationDTO.getBeginningDate()) &&
                                dto.getDegreeTypeId().equals(postEmployeeEducationDTO.getDegreeTypeId()) &&
                                dto.getUniversityId().equals(postEmployeeEducationDTO.getUniversityId()) &&
                                Objects.equals(dto.getFinishingDate(), postEmployeeEducationDTO.getFinishingDate()))
        );
    }

    @Test
    void addEducationByEmployeeId_Returns_NotFound() throws Exception {
        PostEmployeeEducationDTO postEmployeeEducationDTO = new PostEmployeeEducationDTO(1L, 1L, 1L, LocalDate.now().minusDays(1), null);

        Mockito.doThrow(NoSuchElementException.class).when(employeeService).addEducationByEmployeeId(1L, postEmployeeEducationDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/education")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(postEmployeeEducationDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(employeeService).addEducationByEmployeeId(
                Mockito.eq(1L),
                Mockito.argThat(dto ->
                        dto.getMajorId().equals(postEmployeeEducationDTO.getMajorId()) &&
                                dto.getBeginningDate().equals(postEmployeeEducationDTO.getBeginningDate()) &&
                                dto.getDegreeTypeId().equals(postEmployeeEducationDTO.getDegreeTypeId()) &&
                                dto.getUniversityId().equals(postEmployeeEducationDTO.getUniversityId()) &&
                                Objects.equals(dto.getFinishingDate(), postEmployeeEducationDTO.getFinishingDate()))
        );
    }

    @Test
    void getSkillsByEmployeeId_Success() throws Exception {
        GetEmployeeSkillsDTO getEmployeeSkillsDTO = new GetEmployeeSkillsDTO(1L, new ArrayList<>());

        Mockito.when(employeeService.getSkillsByEmployeeId(Mockito.anyLong())).thenReturn(getEmployeeSkillsDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/skills")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("employeeId").value(1))
                .andExpect(jsonPath("skills").value(getEmployeeSkillsDTO.getSkills()));

        Mockito.verify(employeeService, Mockito.times(1)).getSkillsByEmployeeId(Mockito.anyLong());
    }

    @Test
    void getSkillsByEmployeeId_Returns_NotFound() throws Exception {
        Mockito.when(employeeService.getSkillsByEmployeeId(Mockito.anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/skills")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(employeeService, Mockito.times(1)).getSkillsByEmployeeId(Mockito.anyLong());
    }

    @Test
    void deleteEmployeeSkill_Success() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployeeSkill(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1/skills/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployeeSkill(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void deleteEmployeeSkill_Returns_NotFound() throws Exception {
        Mockito.doThrow(NoSuchElementException.class).when(employeeService).deleteEmployeeSkill(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1/skills/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(employeeService, Mockito.times(1)).deleteEmployeeSkill(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void addSkillToEmployee_Success() throws Exception {
        Mockito.doNothing().when(employeeService).addSkillToEmployee(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/skills/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(employeeService, Mockito.times(1)).addSkillToEmployee(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void addSkillToEmployee_Returns_NotFound() throws Exception {
        Mockito.doThrow(NoSuchElementException.class).when(employeeService).addSkillToEmployee(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/1/skills/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(employeeService, Mockito.times(1)).addSkillToEmployee(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void deleteApplicationByApplicationId_Success() throws Exception {
        Mockito.doNothing().when(applicationService).deleteApplicationByApplicationId(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1/application/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(applicationService, Mockito.times(1)).deleteApplicationByApplicationId(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void deleteApplicationByApplicationId_Returns_NotFound() throws Exception {
        Mockito.doThrow(NoSuchElementException.class).when(applicationService).deleteApplicationByApplicationId(Mockito.anyLong(), Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1/application/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(applicationService, Mockito.times(1)).deleteApplicationByApplicationId(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void getApplicationsByEmployeeId_Success() throws Exception {
        Mockito.when(applicationService.getApplicationsByEmployeeAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong())).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/applications")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());

        Mockito.verify(applicationService, Mockito.times(1)).getApplicationsByEmployeeAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong());
    }

    @Test
    void getApplicationsByEmployeeId_Returns_NotFound() throws Exception {
        Mockito.when(applicationService.getApplicationsByEmployeeAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/applications")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(applicationService, Mockito.times(1)).getApplicationsByEmployeeAndCriteria(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong());
    }

    @Test
    void getPositionsAtStartUps_Success() throws Exception {
        GetEmployeePositionDTO getEmployeePositionDTO = GetEmployeePositionDTO.builder().employeeId(1L).build();
        Mockito.when(positionService.getEmployeePositions(Mockito.anyLong(), Mockito.anyLong())).thenReturn(getEmployeePositionDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/positions")
                        .param("startUpId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("employeeId").value(1));

        Mockito.verify(positionService, Mockito.times(1)).getEmployeePositions(Mockito.anyLong(), Mockito.anyLong());
    }

    @Test
    void getPositionsAtStartUps_Returns_NotFound() throws Exception {
        Mockito.when(positionService.getEmployeePositions(Mockito.anyLong(), Mockito.anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1/positions")
                        .param("startUpId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(positionService, Mockito.times(1)).getEmployeePositions(Mockito.anyLong(), Mockito.anyLong());
    }

}