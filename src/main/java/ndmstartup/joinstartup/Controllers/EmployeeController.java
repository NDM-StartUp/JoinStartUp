package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetEducationDTO;
import ndmstartup.joinstartup.DTOs.GetExperienceDTO;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}/experience")
    public ResponseEntity<GetExperienceDTO> getExperienceByEmployeeId(@PathVariable Long employeeId) {
        GetExperienceDTO experienceDTO = employeeService.getExperienceByEmployeeId(employeeId);

        return ResponseEntity.ok(experienceDTO);
    }

    @GetMapping("/{employeeId}/education")
    public ResponseEntity<GetEducationDTO> getEducationByEmployeeId(@PathVariable Long employeeId){
        GetEducationDTO educationDTO = employeeService.getEducationByEmployeeId(employeeId);

        return ResponseEntity.ok(educationDTO);
    }

//    @GetMapping("/{employeeId}/skills")
//    public ResponseEntity<GetSkillsDTO> getSkillsByEmployeeId(@PathVariable Long employeeId){
//        List<GetSillsDTO> educationDTO = employeeService.getSkillsByEmployeeId(employeeId);
//
//        return ResponseEntity.ok(educationDTO);
//    }

}
