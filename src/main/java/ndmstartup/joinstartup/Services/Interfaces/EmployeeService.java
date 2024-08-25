package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetEmployeeApplicationsDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeSkillsDTO;

public interface EmployeeService {
    GetEmployeeExperienceDTO getExperienceByEmployeeId(Long employeeId);

    GetEmployeeEducationDTO getEducationByEmployeeId(Long employeeId);

    GetEmployeeSkillsDTO getSkillsByEmployeeId(Long employeeId);

    void deleteEmployeeSkill(Long employeeId, Long skillId);

    void addSkillToEmployee(Long employeeId, Long skillId);

    GetEmployeeApplicationsDTO getApplicationsByEmployeeId(Long employeeId);

    void deleteApplicationByApplicationId(Long applicationId);
}
