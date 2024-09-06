package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetEmployeePositionDTO;
import ndmstartup.joinstartup.DTOs.GetPositionDTO;
import ndmstartup.joinstartup.Mappers.EmployeeMapper;
import ndmstartup.joinstartup.Mappers.PositionMapper;
import ndmstartup.joinstartup.Models.Position;
import ndmstartup.joinstartup.Models.StartUpEmployee;
import ndmstartup.joinstartup.Repositories.PositionRepository;
import ndmstartup.joinstartup.Repositories.StartUpEmployeeRepository;
import ndmstartup.joinstartup.Services.Interfaces.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

	private final PositionRepository positionRepository;
	private final PositionMapper positionMapper;
	private final StartUpEmployeeRepository startUpEmployeeRepository;
	private final EmployeeMapper employeeMapper;

	@Override
	public List<GetPositionDTO> getAllPositions() {
		List<Position> positions = positionRepository.findAll();
		return positions.stream().map(positionMapper::entityToDTO).toList();
	}
	@Override
	public GetEmployeePositionDTO getEmployeePositions(Long startUpId, Long employeeId) {
		StartUpEmployee startUpEmployee = startUpId==null ?
				startUpEmployeeRepository.findByStartUpIdAndEmployeeId(startUpId, employeeId)
				:startUpEmployeeRepository.findByEmployeeId(employeeId);
		return employeeMapper.entityToPositionDTO(startUpEmployee);
	}
}
