package ndmstartup.joinstartup.Services.Implementations;

import com.sun.net.httpserver.Authenticator;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;
import ndmstartup.joinstartup.Mappers.UniversityMapper;
import ndmstartup.joinstartup.Models.University;
import ndmstartup.joinstartup.Repositories.UniversityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class UniversityServiceImplTest {
	@Mock
	private UniversityRepository universityRepository;

	@Mock
	private UniversityMapper universityMapper;

	@InjectMocks
	private UniversityServiceImpl universityService;

	@Test
	void getAllUniversities_Success(){
		University university1 = new University();
		university1.setId(1L);
		university1.setName("MIT");
		university1.setLocation("Cambridge, MA");

		University university2 = new University();
		university2.setId(2L);
		university2.setName("Stanford");
		university2.setLocation("Stanford, CA");

		University university3 = new University();
		university3.setId(3L);
		university3.setName("Harvard");
		university3.setLocation("Cambridge, MA");

		List<University> universities = Arrays.asList(university1,university2,university3);

		Mockito.when(universityMapper.entityToDTO(university1)).thenReturn(new GetUniversityDTO(1L, "MIT", "Cambridge, MA"));
		Mockito.when(universityMapper.entityToDTO(university2)).thenReturn(new GetUniversityDTO(2L, "Stanford", "Stanford, CA"));
		Mockito.when(universityMapper.entityToDTO(university3)).thenReturn(new GetUniversityDTO(3L, "Harvard", "Cambridge, MA"));

		Mockito.when(universityRepository.findAll()).thenReturn(universities);

		List<GetUniversityDTO> result = universityService.getAllUniversities();

		Assertions.assertEquals(3, result.size());

		Assertions.assertEquals("MIT", result.get(0).getName());
		Assertions.assertEquals("Cambridge, MA", result.get(0).getLocation());

		Assertions.assertEquals("Stanford", result.get(1).getName());
		Assertions.assertEquals("Stanford, CA", result.get(1).getLocation());

		Assertions.assertEquals("Harvard", result.get(2).getName());
		Assertions.assertEquals("Cambridge, MA", result.get(2).getLocation());

		Mockito.verify(universityRepository).findAll();
		Mockito.verify(universityMapper, Mockito.times(3)).entityToDTO(Mockito.any(University.class));
	}

	@Test
	void getAllUniversities_EmptyList(){
		Mockito.when(universityRepository.findAll()).thenReturn(Collections.emptyList());

		List<GetUniversityDTO> result = universityService.getAllUniversities();

		Assertions.assertTrue(result.isEmpty());
		Mockito.verify(universityRepository).findAll();
		Mockito.verify(universityMapper, Mockito.never()).entityToDTO(Mockito.any(University.class));
	}
}
