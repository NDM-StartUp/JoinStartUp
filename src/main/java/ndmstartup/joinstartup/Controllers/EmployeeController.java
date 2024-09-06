package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import ndmstartup.joinstartup.Services.Interfaces.PositionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ApplicationService applicationService;
    private final PositionService positionService;

    @GetMapping("/{employeeId}/experience")
    public ResponseEntity<GetEmployeeExperienceDTO> getExperienceByEmployeeId(@PathVariable Long employeeId) {
        GetEmployeeExperienceDTO experienceDTO = employeeService.getExperienceByEmployeeId(employeeId);

        return ResponseEntity.ok(experienceDTO);
    }

    @PostMapping("/{employeeId}/experience")
    public ResponseEntity<Void> addExperienceByEmployeeId(@PathVariable Long employeeId,
                                                                              @RequestBody PostEmployeeExperienceDTO experience) {
        employeeService.addExperienceByEmployeeId(employeeId, experience);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}/education")
    public ResponseEntity<GetEmployeeEducationDTO> getEducationByEmployeeId(@PathVariable Long employeeId) {
        GetEmployeeEducationDTO educationDTO = employeeService.getEducationByEmployeeId(employeeId);

        return ResponseEntity.ok(educationDTO);
    }

    @PostMapping("/{employeeId}/education")
    public ResponseEntity<Void> addEducationByEmployeeId(@PathVariable Long employeeId,
    @RequestBody PostEmployeeEducationDTO education) {
        employeeService.addEducationByEmployeeId(employeeId, education);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}/skills")
    public ResponseEntity<GetEmployeeSkillsDTO> getSkillsByEmployeeId(@PathVariable Long employeeId) {
        GetEmployeeSkillsDTO educationDTO = employeeService.getSkillsByEmployeeId(employeeId);

        return ResponseEntity.ok(educationDTO);
    }

    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> deleteEmployeeSkill(@PathVariable Long employeeId, @PathVariable Long skillId) {
        employeeService.deleteEmployeeSkill(employeeId, skillId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> addSkillToEmployee(@PathVariable Long employeeId, @PathVariable Long skillId) {
        employeeService.addSkillToEmployee(employeeId, skillId);

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/{employeeId}/applications")
//    public ResponseEntity<GetEmployeeApplicationsDTO> getApplicationsByEmployeeId(@PathVariable Long employeeId){
//        GetEmployeeApplicationsDTO applications = employeeService.getApplicationsByEmployeeId(employeeId);
//
//        return ResponseEntity.ok(applications);
//    }

    @DeleteMapping("/application/{applicationId}")
    public ResponseEntity<Void> deleteApplicationByApplicationId(@PathVariable Long applicationId) {
        employeeService.deleteApplicationByApplicationId(applicationId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{employeeId}/applications")
    public ResponseEntity<List<GetApplicationDTO>> getApplications(
            @RequestParam(required = false) String applicationStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate, @PathVariable Long employeeId) {

        List<GetApplicationDTO> applications = applicationService.getApplicationsByEmployeeAndCriteria(applicationStatus, startDate, endDate, employeeId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{employeeId}/positions")
    public ResponseEntity<GetEmployeePositionDTO> getPositionsAtStartUps(
            @RequestParam(required = false) Long startUpId, Long employeeId) {
        GetEmployeePositionDTO employeePositions = positionService.getEmployeePositions(startUpId, employeeId);

        return ResponseEntity.ok(employeePositions);
    }
}
