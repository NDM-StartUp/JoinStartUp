package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetSkillDTO;
import ndmstartup.joinstartup.Mappers.SkillMapper;
import ndmstartup.joinstartup.Models.Skill;
import ndmstartup.joinstartup.Repositories.SkillRepository;
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
public class SkillServiceImplTest {
	@Mock
	private SkillRepository skillRepository;
	@Mock
	private SkillMapper skillMapper;
	@InjectMocks
	private SkillServiceImpl skillService;

	private Skill createSkill(Long id, String name) {
		Skill skill = new Skill();
		skill.setId(id);
		skill.setName(name);

		return skill;
	}
	@Test
	void getAllSkills_Success() {
		List<Skill> skills = new ArrayList<>();
		skills.add(createSkill(1L, "Java"));
		skills.add(createSkill(2L, "Python"));

		List<GetSkillDTO> expectedDTOs = new ArrayList<>();
		expectedDTOs.add(new GetSkillDTO(1L, "Java"));
		expectedDTOs.add(new GetSkillDTO(2L, "Python"));

		when(skillRepository.findAll()).thenReturn(skills);
		when(skillMapper.entityToDTO(skills.get(0))).thenReturn(expectedDTOs.get(0));
		when(skillMapper.entityToDTO(skills.get(1))).thenReturn(expectedDTOs.get(1));

		List<GetSkillDTO> result = skillService.getAllSkills();

		verify(skillRepository, times(1)).findAll();
		verify(skillMapper, times(2)).entityToDTO(any(Skill.class));
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(expectedDTOs, result);
	}

	@Test
	void getAllSkills_NoSkillsFound_ReturnsEmptyList() {
		when(skillRepository.findAll()).thenReturn(new ArrayList<>());

		List<GetSkillDTO> result = skillService.getAllSkills();

		verify(skillRepository, times(1)).findAll();
		verify(skillMapper, never()).entityToDTO(any());
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}
