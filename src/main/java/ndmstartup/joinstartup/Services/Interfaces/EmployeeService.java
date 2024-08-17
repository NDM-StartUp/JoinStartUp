package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetEducationDTO;
import ndmstartup.joinstartup.DTOs.GetExperienceDTO;

public interface EmployeeService {
    GetExperienceDTO getExperienceByEmployeeId(Long employeeId);

    GetEducationDTO getEducationByEmployeeId(Long employeeId);
}
