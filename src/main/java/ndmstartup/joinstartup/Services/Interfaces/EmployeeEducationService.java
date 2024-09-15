package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;

public interface EmployeeEducationService {
	GetUniversityDTO getUniversityByEducationId(Long educationId);
	GetMajorDTO getMajorByEducationId(Long educationId);

	GetDegreeTypeDTO getDegreeTypeByEducationId(Long educationId);
}
