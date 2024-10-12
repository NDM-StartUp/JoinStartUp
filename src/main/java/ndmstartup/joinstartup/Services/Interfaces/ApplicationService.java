package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetApplicationDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationService {
	List<GetApplicationDTO> getApplicationsByStartUpAndCriteria(String applicationStatus, LocalDateTime startDate, LocalDateTime  endDate, Long startUpId);

	void deleteApplicationByApplicationId(Long applicationId, Long employeeId);

	List<GetApplicationDTO> getApplicationsByEmployeeAndCriteria(String applicationStatus, LocalDateTime startDate, LocalDateTime endDate, Long employeeId);
}
