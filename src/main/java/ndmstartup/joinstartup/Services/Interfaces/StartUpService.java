package ndmstartup.joinstartup.Services.Interfaces;


import ndmstartup.joinstartup.DTOs.*;

import java.util.List;

public interface StartUpService {
	List<GetStartUpDTO> getAllStartUps();
	GetStartUpDTO getStartUpByStartUpId(Long startUpId);
	GetStartUpIdEmployeeDTO getEmployeesByStartUpId(Long startUpId);
	GetStartUpCompanyNameEmployeeDTO getEmployeesByStartUpCompanyName(String companyName);
	GetStartUpLocationEmployeeDTO getEmployeesByStartUpLocation(String location);
	GetStartUpIsPaidEmployeeDTO getEmployeesByStartUpIsPaid(boolean isPaid);
}
