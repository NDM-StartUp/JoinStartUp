package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetPositionDTO;
import ndmstartup.joinstartup.Mappers.PositionMapper;
import ndmstartup.joinstartup.Models.Position;
import ndmstartup.joinstartup.Repositories.PositionRepository;
import ndmstartup.joinstartup.Services.Interfaces.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

	private final PositionRepository positionRepository;
	private final PositionMapper positionMapper;

	@Override
	public List<GetPositionDTO> getAllPositions() {
		List<Position> positions = positionRepository.findAll();
		return positions.stream().map(positionMapper::entityToDTO).toList();
	}
}
