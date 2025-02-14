package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.Mappers.MajorMapper;
import ndmstartup.joinstartup.Models.Major;
import ndmstartup.joinstartup.Repositories.MajorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MajorServiceImplTest {
	@Mock
	private MajorRepository majorRepository;
	@Mock
	private MajorMapper majorMapper;
	@InjectMocks
	private MajorServiceImpl majorService;

	@Test
	void getAllMajors_Success() {
		List<Major> majors = new ArrayList<>();
		majors.add(new Major(1L, "Computer Science", new ArrayList<>()));
		majors.add(new Major(2L, "Mechanical Engineering", new ArrayList<>()));

		List<GetMajorDTO> expectedDTOs = new ArrayList<>();
		expectedDTOs.add(new GetMajorDTO(1L, "Computer Science"));
		expectedDTOs.add(new GetMajorDTO(2L, "Mechanical Engineering"));

		when(majorRepository.findAll()).thenReturn(majors);
		when(majorMapper.entityToDTO(majors.get(0))).thenReturn(expectedDTOs.get(0));
		when(majorMapper.entityToDTO(majors.get(1))).thenReturn(expectedDTOs.get(1));

		List<GetMajorDTO> result = majorService.getAllMajors();

		verify(majorRepository, times(1)).findAll();
		verify(majorMapper, times(2)).entityToDTO(any(Major.class));
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(expectedDTOs, result);
	}

	@Test
	void getAllMajors_NoMajorsFound_ReturnsEmptyList() {
		when(majorRepository.findAll()).thenReturn(new ArrayList<>());

		List<GetMajorDTO> result = majorService.getAllMajors();

		verify(majorRepository, times(1)).findAll();
		verify(majorMapper, never()).entityToDTO(any());
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}
