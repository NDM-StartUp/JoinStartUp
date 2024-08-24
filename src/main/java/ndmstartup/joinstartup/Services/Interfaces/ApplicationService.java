package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetApplicationDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ApplicationService {
	public List<GetApplicationDTO> getApplicationsByCriteria(String startupStatus, Date startDate, Date  endDate);
}
