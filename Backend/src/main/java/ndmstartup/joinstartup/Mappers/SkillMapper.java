package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSkillDTO;
import ndmstartup.joinstartup.Models.Skill;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SkillMapper {
	public GetSkillDTO entityToDTO (Skill skill) {
		return GetSkillDTO.builder()
				.id(skill.getId())
				.name(skill.getName())
				.build();
	}
}
