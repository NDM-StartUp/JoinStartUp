package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import ndmstartup.joinstartup.Services.Interfaces.PositionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @GetMapping("/{employeeId}/experience")
    public ResponseEntity<GetEmployeeExperienceDTO> getExperienceByEmployeeId(@PathVariable Long employeeId) {
        GetEmployeeExperienceDTO experienceDTO = employeeService.getExperienceByEmployeeId(employeeId);

        return ResponseEntity.ok(experienceDTO);
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @PostMapping("/{employeeId}/experience")
    public ResponseEntity<Void> addExperienceByEmployeeId(@PathVariable Long employeeId,
                                                          @RequestBody PostEmployeeExperienceDTO experience) {
        employeeService.addExperienceByEmployeeId(employeeId, experience);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @GetMapping("/{employeeId}/education")
    public ResponseEntity<GetEmployeeEducationDTO> getEducationByEmployeeId(@PathVariable Long employeeId) {
        GetEmployeeEducationDTO educationDTO = employeeService.getEducationByEmployeeId(employeeId);

        return ResponseEntity.ok(educationDTO);
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @PostMapping("/{employeeId}/education")
    public ResponseEntity<Void> addEducationByEmployeeId(@PathVariable Long employeeId,
                                                         @RequestBody PostEmployeeEducationDTO education) {
        employeeService.addEducationByEmployeeId(employeeId, education);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @GetMapping("/{employeeId}/skills")
    public ResponseEntity<GetEmployeeSkillsDTO> getSkillsByEmployeeId(@PathVariable Long employeeId) {
        GetEmployeeSkillsDTO educationDTO = employeeService.getSkillsByEmployeeId(employeeId);

        return ResponseEntity.ok(educationDTO);
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> deleteEmployeeSkill(@PathVariable Long employeeId, @PathVariable Long skillId) {
        employeeService.deleteEmployeeSkill(employeeId, skillId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @PostMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Void> addSkillToEmployee(@PathVariable Long employeeId, @PathVariable Long skillId) {
        employeeService.addSkillToEmployee(employeeId, skillId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @DeleteMapping("/{employeeId}/application/{applicationId}")
    public ResponseEntity<Void> deleteApplicationByApplicationId(@PathVariable Long applicationId,
                                                                 @PathVariable Long employeeId) {
        applicationService.deleteApplicationByApplicationId(applicationId, employeeId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @GetMapping("/{employeeId}/applications")
    public ResponseEntity<List<GetApplicationDTO>> getApplicationsByEmployeeId(
            @RequestParam(required = false) String applicationStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @PathVariable Long employeeId) {

        List<GetApplicationDTO> applications = applicationService.getApplicationsByEmployeeAndCriteria(applicationStatus, startDate, endDate, employeeId);
        return ResponseEntity.ok(applications);
    }

    @PreAuthorize("#employeeId == authentication.principal.id ")
    @GetMapping("/{employeeId}/positions")
    public ResponseEntity<GetEmployeePositionDTO> getPositionsAtStartUps(
            @RequestParam(required = false) Long startUpId, @PathVariable Long employeeId) {
        GetEmployeePositionDTO employeePositions = positionService.getEmployeePositions(startUpId, employeeId);

        return ResponseEntity.ok(employeePositions);
    }
}
