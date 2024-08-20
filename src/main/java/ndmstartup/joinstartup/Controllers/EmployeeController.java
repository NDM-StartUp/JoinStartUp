package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employeeId}/experience")
    public ResponseEntity<GetEmployeeExperienceDTO> getExperienceByEmployeeId(@PathVariable Long employeeId) {
        GetEmployeeExperienceDTO experienceDTO = employeeService.getExperienceByEmployeeId(employeeId);

        return ResponseEntity.ok(experienceDTO);
    }

    @GetMapping("/{employeeId}/education")
    public ResponseEntity<GetEmployeeEducationDTO> getEducationByEmployeeId(@PathVariable Long employeeId){
        GetEmployeeEducationDTO educationDTO = employeeService.getEducationByEmployeeId(employeeId);

        return ResponseEntity.ok(educationDTO);
    }

//    @GetMapping("/{employeeId}/skills")
//    public ResponseEntity<GetEmployeeSkillsDTO> getSkillsByEmployeeId(@PathVariable Long employeeId){
//        List<GetEmployeeSkillsDTO> educationDTO = employeeService.getSkillsByEmployeeId(employeeId);
//
//        return ResponseEntity.ok(educationDTO);
//    }

}
