package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetEmployeePositionDTO;
import ndmstartup.joinstartup.DTOs.GetPositionDTO;

import java.util.List;

public interface PositionService {
	List<GetPositionDTO> getAllPositions();
	GetEmployeePositionDTO getEmployeePositions(Long startUpId, Long employeeId);
}
