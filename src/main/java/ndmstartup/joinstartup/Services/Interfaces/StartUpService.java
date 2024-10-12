package ndmstartup.joinstartup.Services.Interfaces;


import ndmstartup.joinstartup.DTOs.*;

import java.util.List;

public interface StartUpService {
	List<GetStartUpDTO> getAllStartUps();
	GetStartUpDTO getStartUpByStartUpId(Long startUpId);
	GetStartUpIdEmployeeDTO getEmployeesByStartUpId(Long startUpId);

	List<GetStartUpDTO> searchStartUpByCriteria(Long startUpId, String companyName, String location, Boolean isPaid);

	void addStartUp (PostStartUpDTO postStartUpDTO);
	void updateStartUp (Long startUpId, PostStartUpDTO postStartUpDTO);

	void deleteStartUp (Long startUpId);
	List<GetStartUpDTO> getStartUpsByProgressStatus(String progressStatusName);

	void updateStartUpStatus(Long startUpId, String progressStatusName);
	void deleteStartUpSkills(Long startUpId);

	List<GetStartUpStatusDTO> getAllStartUpProgressStatuses(Long startUpId);


/*	GetStartUpCompanyNameEmployeeDTO getEmployeesByStartUpCompanyName(String companyName);
	GetStartUpLocationEmployeeDTO getEmployeesByStartUpLocation(String location);
	GetStartUpIsPaidEmployeeDTO getEmployeesByStartUpIsPaid(boolean isPaid);*/
}
