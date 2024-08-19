package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetPositionDTO;

import java.util.List;

public interface PositionService {
	List<GetPositionDTO> getAllPositions();
}
