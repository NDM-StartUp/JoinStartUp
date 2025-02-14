package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSkillDTO;
import ndmstartup.joinstartup.Services.Interfaces.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skills")
public class SkillController {

	private final SkillService skillService;

	@GetMapping("/all")
	public ResponseEntity<List<GetSkillDTO>> getAllSkills() {
		List<GetSkillDTO> skills = skillService.getAllSkills();
		return ResponseEntity.ok(skills);
	}
}
