package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetApplicationDTO;

import java.util.Date;
import java.util.List;

public interface ApplicationService {
	List<GetApplicationDTO> getApplicationsByStartUpAndCriteria(String applicationStatus, Date startDate, Date  endDate, Long startUpId);

	void deleteApplicationByApplicationId(Long applicationId, Long employeeId);

	List<GetApplicationDTO> getApplicationsByEmployeeAndCriteria(String applicationStatus, Date startDate, Date endDate, Long employeeId);
}
