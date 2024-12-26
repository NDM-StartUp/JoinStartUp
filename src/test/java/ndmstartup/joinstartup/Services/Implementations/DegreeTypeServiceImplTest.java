package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.Mappers.DegreeTypeMapper;
import ndmstartup.joinstartup.Models.DegreeType;
import ndmstartup.joinstartup.Repositories.DegreeTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DegreeTypeServiceImplTest {
	@Mock
	private DegreeTypeRepository degreeTypeRepository;

	@Mock
	private DegreeTypeMapper degreeTypeMapper;

	@InjectMocks
	private DegreeTypeServiceImpl degreeTypeService;

	private DegreeType degreeType1;
	private DegreeType degreeType2;

	@BeforeEach
	void setUp() {
		degreeType1 = new DegreeType();
		degreeType1.setId(1L);
		degreeType1.setName("Bachelor");

		degreeType2 = new DegreeType();
		degreeType2.setId(2L);
		degreeType2.setName("Master");
	}

	@Test
	void getAllDegreeTypes_ReturnsDegreeTypes() {
		List<DegreeType> degreeTypes = Arrays.asList(degreeType1, degreeType2);

		Mockito.when(degreeTypeRepository.findAll()).thenReturn(degreeTypes);
		Mockito.when(degreeTypeMapper.entityToDTO(degreeType1)).thenReturn(new GetDegreeTypeDTO(1L, "Bachelor"));
		Mockito.when(degreeTypeMapper.entityToDTO(degreeType2)).thenReturn(new GetDegreeTypeDTO(2L, "Master"));

		List<GetDegreeTypeDTO> result = degreeTypeService.getAllDegreeTypes();

		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("Bachelor", result.get(0).getName());
		Assertions.assertEquals("Master", result.get(1).getName());

		Mockito.verify(degreeTypeRepository).findAll();
		Mockito.verify(degreeTypeMapper, Mockito.times(2)).entityToDTO(Mockito.any(DegreeType.class));
	}

	@Test
	void getAllDegreeTypes_ReturnsEmptyList_WhenNoDegreeTypesExist() {
		Mockito.when(degreeTypeRepository.findAll()).thenReturn(Collections.emptyList());

		List<GetDegreeTypeDTO> result = degreeTypeService.getAllDegreeTypes();

		Assertions.assertTrue(result.isEmpty());

		Mockito.verify(degreeTypeRepository).findAll();
		Mockito.verify(degreeTypeMapper, Mockito.never()).entityToDTO(Mockito.any(DegreeType.class));
	}
}
