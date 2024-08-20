package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetEmployeeApplicationsDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeSkillsDTO;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{employeeId}/skills")
    public ResponseEntity<GetEmployeeSkillsDTO> getSkillsByEmployeeId(@PathVariable Long employeeId){
        GetEmployeeSkillsDTO educationDTO = employeeService.getSkillsByEmployeeId(employeeId);

        return ResponseEntity.ok(educationDTO);
    }

    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> deleteEmployeeSkill(@PathVariable Long employeeId, @PathVariable Long skillId){
        employeeService.deleteEmployeeSkill(employeeId,skillId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> addSkillToEmployee(@PathVariable Long employeeId, @PathVariable Long skillId){
        employeeService.addSkillToEmployee(employeeId,skillId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}/application")
    public ResponseEntity<GetEmployeeApplicationsDTO> getApplicationsByEmployeeId(@PathVariable Long employeeId){
        GetEmployeeApplicationsDTO applications = employeeService.getApplicationsByEmployeeId(employeeId);

        return ResponseEntity.ok(applications);
    }
}
