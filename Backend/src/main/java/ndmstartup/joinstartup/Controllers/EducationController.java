package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;
import ndmstartup.joinstartup.Services.Interfaces.DegreeTypeService;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeEducationService;
import ndmstartup.joinstartup.Services.Interfaces.MajorService;
import ndmstartup.joinstartup.Services.Interfaces.UniversityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/education")
@RequiredArgsConstructor
public class EducationController {
	private final UniversityService universityService;
	private final MajorService majorService;
	private final DegreeTypeService degreeTypeService;
	private final EmployeeEducationService employeeEducationService;

	@GetMapping("/universities")
	public ResponseEntity<List<GetUniversityDTO>> getAllUniversities() {
		List<GetUniversityDTO> universityList = universityService.getAllUniversities();
		return ResponseEntity.ok(universityList);
	}

	@GetMapping("/majors")
	public ResponseEntity<List<GetMajorDTO>> getAllMajors() {
		List<GetMajorDTO> majorList = majorService.getAllMajors();
		return ResponseEntity.ok(majorList);
	}

	@GetMapping("/degree-types")
	public ResponseEntity<List<GetDegreeTypeDTO>> getAllDegreeTypes() {
		List<GetDegreeTypeDTO> degreeTypeList = degreeTypeService.getAllDegreeTypes();
		return ResponseEntity.ok(degreeTypeList);
	}

	@GetMapping("/university/{educationId}")
	public ResponseEntity<GetUniversityDTO> getUniversityByEducationId(@PathVariable Long educationId) {
		GetUniversityDTO university = employeeEducationService.getUniversityByEducationId(educationId);
		return ResponseEntity.ok(university);
	}

	@GetMapping("/major/{educationId}")
	public ResponseEntity<GetMajorDTO> getMajorByEducationId(@PathVariable Long educationId) {
		GetMajorDTO major = employeeEducationService.getMajorByEducationId(educationId);
		return ResponseEntity.ok(major);
	}

	@GetMapping("/degree/{educationId}")
	public ResponseEntity<GetDegreeTypeDTO> getDegreeTypeByEducationId(@PathVariable Long educationId) {
		GetDegreeTypeDTO degreeType = employeeEducationService.getDegreeTypeByEducationId(educationId);
		return ResponseEntity.ok(degreeType);
	}
}
