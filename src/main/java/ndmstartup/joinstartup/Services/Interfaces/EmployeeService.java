package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;

public interface EmployeeService {
    GetEmployeeExperienceDTO getExperienceByEmployeeId(Long employeeId);

    GetEmployeeEducationDTO getEducationByEmployeeId(Long employeeId);
}
