package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.*;

public interface EmployeeService {
    GetEmployeeExperienceDTO getExperienceByEmployeeId(Long employeeId);

    GetEmployeeEducationDTO getEducationByEmployeeId(Long employeeId);

    GetEmployeeSkillsDTO getSkillsByEmployeeId(Long employeeId);

    void deleteEmployeeSkill(Long employeeId, Long skillId);

    void addSkillToEmployee(Long employeeId, Long skillId);

    void addExperienceByEmployeeId(Long employeeId, PostEmployeeExperienceDTO experience);

    void addEducationByEmployeeId(Long employeeId, PostEmployeeEducationDTO education);
}
