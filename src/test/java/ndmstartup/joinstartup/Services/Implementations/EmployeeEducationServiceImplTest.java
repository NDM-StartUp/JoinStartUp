package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;
import ndmstartup.joinstartup.Mappers.DegreeTypeMapper;
import ndmstartup.joinstartup.Mappers.MajorMapper;
import ndmstartup.joinstartup.Mappers.UniversityMapper;
import ndmstartup.joinstartup.Models.DegreeType;
import ndmstartup.joinstartup.Models.EmployeeEducation;
import ndmstartup.joinstartup.Models.Major;
import ndmstartup.joinstartup.Models.University;
import ndmstartup.joinstartup.Repositories.EmployeeEducationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeEducationServiceImplTest {

    @Mock
    private EmployeeEducationRepository employeeEducationRepository;

    @Mock
    private UniversityMapper universityMapper;

    @Mock
    private MajorMapper majorMapper;

    @Mock
    private DegreeTypeMapper degreeTypeMapper;

    @InjectMocks
    private EmployeeEducationServiceImpl employeeEducationService;

    @Mock
    private EmployeeEducation education;

    @Test
    void getUniversityByEducationId_Success() {
        Long educationId = 1L;
        Mockito.when(employeeEducationRepository.findById(educationId)).thenReturn(Optional.of(education));
        University university = new University();
        GetUniversityDTO getUniversityDTO = new GetUniversityDTO();
        Mockito.when(universityMapper.entityToDTO(university)).thenReturn(getUniversityDTO);
        Mockito.when(education.getUniversity()).thenReturn(university);

        GetUniversityDTO universityByEducationId = employeeEducationService.getUniversityByEducationId(educationId);

        Assertions.assertNotNull(universityByEducationId);
        Assertions.assertEquals(getUniversityDTO, universityByEducationId);
    }

    @Test
    void getUniversityByEducationId_ThrowsNoSuchElementException_WhenEmployeeEducationNotFound() {
        Long educationId = 1L;
        Mockito.when(employeeEducationRepository.findById(educationId)).thenReturn(Optional.empty());
        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            employeeEducationService.getUniversityByEducationId(educationId);
        });

        Assertions.assertEquals("EmployeeEducation not found with id: " + educationId, exception.getMessage());

    }

    @Test
    void getMajorByEducationId_Success() {
        Long educationId = 1L;
        Mockito.when(employeeEducationRepository.findById(educationId)).thenReturn(Optional.of(education));
        Major major = new Major();
        GetMajorDTO getMajorDTO = new GetMajorDTO();
        Mockito.when(majorMapper.entityToDTO(major)).thenReturn(getMajorDTO);
        Mockito.when(education.getMajor()).thenReturn(major);

        GetMajorDTO majorByEducationId = employeeEducationService.getMajorByEducationId(educationId);

        Assertions.assertNotNull(majorByEducationId);
        Assertions.assertEquals(getMajorDTO, majorByEducationId);
    }

    @Test
    void getMajorByEducationId_ThrowsNoSuchElementException_WhenEmployeeEducationNotFound() {
        Long educationId = 1L;
        Mockito.when(employeeEducationRepository.findById(educationId)).thenReturn(Optional.empty());
        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            employeeEducationService.getUniversityByEducationId(educationId);
        });

        Assertions.assertEquals("EmployeeEducation not found with id: " + educationId, exception.getMessage());

    }

    @Test
    void getDegreeTypeByEducationId_Success() {
        Long educationId = 1L;
        Mockito.when(employeeEducationRepository.findById(educationId)).thenReturn(Optional.of(education));
        DegreeType degreeType = new DegreeType();
        GetDegreeTypeDTO getDegreeTypeDTO = new GetDegreeTypeDTO();
        Mockito.when(degreeTypeMapper.entityToDTO(degreeType)).thenReturn(getDegreeTypeDTO);
        Mockito.when(education.getDegreeType()).thenReturn(degreeType);

        GetDegreeTypeDTO degreeTypeByEducationId = employeeEducationService.getDegreeTypeByEducationId(educationId);

        Assertions.assertNotNull(degreeTypeByEducationId);
        Assertions.assertEquals(getDegreeTypeDTO, degreeTypeByEducationId);
    }

    @Test
    void getDegreeTypeByEducationId_ThrowsNoSuchElementException_WhenEmployeeEducationNotFound() {
        Long educationId = 1L;
        Mockito.when(employeeEducationRepository.findById(educationId)).thenReturn(Optional.empty());
        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            employeeEducationService.getUniversityByEducationId(educationId);
        });

        Assertions.assertEquals("EmployeeEducation not found with id: " + educationId, exception.getMessage());
    }
}