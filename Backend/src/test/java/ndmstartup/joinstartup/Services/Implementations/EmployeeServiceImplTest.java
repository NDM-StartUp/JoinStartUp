package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Mappers.EducationMapper;
import ndmstartup.joinstartup.Mappers.EmployeeMapper;
import ndmstartup.joinstartup.Models.*;
import ndmstartup.joinstartup.Repositories.EmployeeEducationRepository;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Repositories.SkillRepository;
import ndmstartup.joinstartup.Repositories.WorkEmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private WorkEmployeeRepository workEmployeeRepository;

    @Mock
    private EmployeeEducationRepository employeeEducationRepository;

    @Mock
    private EducationMapper educationMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private Employee employee;

    @Mock
    private Skill skill;


    @Test
    void getExperienceByEmployeeId_Success() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        GetEmployeeExperienceDTO employeeExperienceDTO = new GetEmployeeExperienceDTO(employeeId, new ArrayList<>());

        Mockito.when(employeeMapper.entityToExperienceDTO(employee)).thenReturn(employeeExperienceDTO);

        GetEmployeeExperienceDTO experienceByEmployeeId = employeeService.getExperienceByEmployeeId(employeeId);


        Assertions.assertNotNull(experienceByEmployeeId);
        Assertions.assertEquals(employeeExperienceDTO, experienceByEmployeeId);

    }

    @Test
    void getExperienceByEmployeeId_ThrowsNoSuchElementException_WhenEmployeeNotFound() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.getExperienceByEmployeeId(employeeId));

        Assertions.assertEquals(exception.getMessage(), "Employee not found with id " + employeeId);

    }

    @Test
    void getEducationByEmployeeId_Success() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        GetEmployeeEducationDTO employeeEducationDTO = new GetEmployeeEducationDTO(employeeId, new ArrayList<>());

        Mockito.when(employeeMapper.entityToEducationDTO(employee)).thenReturn(employeeEducationDTO);

        GetEmployeeEducationDTO educationByEmployeeId = employeeService.getEducationByEmployeeId(employeeId);


        Assertions.assertNotNull(educationByEmployeeId);
        Assertions.assertEquals(educationByEmployeeId, employeeEducationDTO);

    }

    @Test
    void getEducationByEmployeeId_ThrowsNoSuchElementException_WhenEmployeeNotFound() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.getEducationByEmployeeId(employeeId));

        Assertions.assertEquals(exception.getMessage(), "Employee not found with id " + employeeId);

    }

    @Test
    void getSkillsByEmployeeId_Success() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        GetEmployeeSkillsDTO getEmployeeSkillsDTO = new GetEmployeeSkillsDTO(employeeId, new ArrayList<>());

        Mockito.when(employeeMapper.entityToSkillsDTO(employee)).thenReturn(getEmployeeSkillsDTO);

        GetEmployeeSkillsDTO skillsByEmployeeId = employeeService.getSkillsByEmployeeId(employeeId);


        Assertions.assertNotNull(skillsByEmployeeId);
        Assertions.assertEquals(getEmployeeSkillsDTO, skillsByEmployeeId);

    }

    @Test
    void getSkillsByEmployeeId_ThrowsNoSuchElementException_WhenEmployeeNotFound() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.getSkillsByEmployeeId(employeeId));

        Assertions.assertEquals(exception.getMessage(), "Employee not found with id " + employeeId);

    }


    @Test
    void deleteEmployeeSkill_Success() {
        Long employeeId = 1L;
        Long skillId = 1L;

        List<Skill> mockSkills = new ArrayList<>();
        mockSkills.add(skill);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(skillRepository.findById(skillId)).thenReturn(Optional.of(skill));
        Mockito.when(employee.getSkills()).thenReturn(mockSkills);

        employeeService.deleteEmployeeSkill(employeeId, skillId);

        Mockito.verify(employeeRepository).save(employee);
        Assertions.assertFalse(mockSkills.contains(skill));
    }

    @Test
    void deleteEmployeeSkill_ThrowsNoSuchElementException_WhenEmployeeNotFound() {
        Long employeeId = 1L;
        Long skillId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.deleteEmployeeSkill(employeeId, skillId));

        Assertions.assertEquals(exception.getMessage(), "Employee not found with id " + employeeId);
    }

    @Test
    void deleteEmployeeSkill_ThrowsNoSuchElementException_WhenSkillNotFound() {
        Long employeeId = 1L;
        Long skillId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(skillRepository.findById(skillId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.deleteEmployeeSkill(employeeId, skillId));

        Assertions.assertEquals(exception.getMessage(), "Skill not found with id " + employeeId);
    }

    @Test
    void deleteEmployeeSkill_ThrowsNoSuchElementException_WhenEmployeeDoesNotHaveSkill() {
        Long employeeId = 1L;
        Long skillId = 1L;

        List<Skill> mockSkills = new ArrayList<>();

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(skillRepository.findById(skillId)).thenReturn(Optional.of(skill));
        Mockito.when(employee.getSkills()).thenReturn(mockSkills);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.deleteEmployeeSkill(employeeId, skillId));

        Assertions.assertEquals(exception.getMessage(), "Employee with id: " + employeeId + " does not have skill with id: " + skillId);
    }

    @Test
    void addSkillToEmployee_Success() {
        Long employeeId = 1L;
        Long skillId = 1L;

        List<Skill> mockSkills = new ArrayList<>();
        mockSkills.add(skill);

        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(skillRepository.findById(skillId)).thenReturn(Optional.of(skill));
        Mockito.when(employee.getSkills()).thenReturn(mockSkills);

        employeeService.addSkillToEmployee(employeeId, skillId);
        Mockito.verify(employeeRepository).save(employee);
        Assertions.assertTrue(mockSkills.contains(skill));
    }

    @Test
    void addSkillToEmployee_ThrowsNoSuchElementException_WhenEmployeeNotFound() {
        Long employeeId = 1L;
        Long skillId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.addSkillToEmployee(employeeId, skillId));

        Assertions.assertEquals(exception.getMessage(), "Employee not found with id " + employeeId);
    }

    @Test
    void addSkillToEmployee_ThrowsNoSuchElementException_WhenSkillNotFound() {
        Long employeeId = 1L;
        Long skillId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(skillRepository.findById(skillId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.addSkillToEmployee(employeeId, skillId));

        Assertions.assertEquals(exception.getMessage(), "Skill not found with id " + employeeId);
    }

    @Test
    void addExperienceByEmployeeId_Success() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        PostEmployeeExperienceDTO experience = new PostEmployeeExperienceDTO(new Position(), "LOL", LocalDate.now().minusDays(2), null);

        employeeService.addExperienceByEmployeeId(employeeId, experience);

        ArgumentCaptor<WorkEmployee> captor = ArgumentCaptor.forClass(WorkEmployee.class);
        Mockito.verify(workEmployeeRepository).save(captor.capture());
        WorkEmployee savedWorkEmployee = captor.getValue();
        Assertions.assertEquals(savedWorkEmployee.getEmployee(), employee);
        Assertions.assertEquals(savedWorkEmployee.getPosition(), experience.getPosition());
        Assertions.assertEquals(savedWorkEmployee.getCompanyName(), experience.getCompanyName());
        Assertions.assertEquals(savedWorkEmployee.getStartingDate(), experience.getStartingDate());
        Assertions.assertEquals(savedWorkEmployee.getEndingDate(), experience.getEndingDate());
        Assertions.assertEquals(savedWorkEmployee.getPosition(), experience.getPosition());

    }

    @Test
    void addExperienceByEmployeeId_ThrowsNoSuchElementException_WhenEmployeeNotFound() {
        Long employeeId = 1L;
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        PostEmployeeExperienceDTO experience = new PostEmployeeExperienceDTO(new Position(), "LOL", LocalDate.now().minusDays(2), null);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.addExperienceByEmployeeId(employeeId, experience));

        Assertions.assertEquals(exception.getMessage(), "Employee not found with id " + employeeId);
    }

    @Test
    void addEducationByEmployeeId_Success() {
        Long employeeId = 3L;
        PostEmployeeEducationDTO education = new PostEmployeeEducationDTO(1L, 1L, 1L, LocalDate.now().minusDays(2), null);
        EmployeeEducation employeeEducation = new EmployeeEducation();
        Mockito.when(educationMapper.postDtoToEntity(education, employee)).thenReturn(employeeEducation);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        employeeService.addEducationByEmployeeId(employeeId, education);

        Mockito.verify(employeeEducationRepository).save(employeeEducation);
    }

    @Test
    void addEducationByEmployeeId_ThrowsNoSuchElementException_WhenEmployeeNotFound() {
        Long employeeId = 1L;
        PostEmployeeEducationDTO education = new PostEmployeeEducationDTO(1L, 1L, 1L, LocalDate.now().minusDays(2), null);
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> employeeService.addEducationByEmployeeId(employeeId, education));

        Assertions.assertEquals(exception.getMessage(), "Employee not found with id " + employeeId);


    }
}