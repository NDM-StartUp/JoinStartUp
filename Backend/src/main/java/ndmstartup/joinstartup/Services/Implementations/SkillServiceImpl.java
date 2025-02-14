package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSkillDTO;
import ndmstartup.joinstartup.Mappers.SkillMapper;
import ndmstartup.joinstartup.Models.Skill;
import ndmstartup.joinstartup.Repositories.SkillRepository;
import ndmstartup.joinstartup.Services.Interfaces.SkillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

	private final SkillRepository skillRepository;
	private final SkillMapper skillMapper;

	@Override
	public List<GetSkillDTO> getAllSkills() {
		List<Skill> skills = skillRepository.findAll();
		return skills.stream().map(skillMapper::entityToDTO).toList();
	}
}
