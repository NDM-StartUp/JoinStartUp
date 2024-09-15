package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetDegreeTypeDTO;
import ndmstartup.joinstartup.Mappers.DegreeTypeMapper;
import ndmstartup.joinstartup.Repositories.DegreeTypeRepository;
import ndmstartup.joinstartup.Services.Interfaces.DegreeTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DegreeTypeServiceImpl implements DegreeTypeService {
	private final DegreeTypeRepository degreeTypeRepository;
	private final DegreeTypeMapper degreeTypeMapper;

	@Override
	@Transactional
	public List<GetDegreeTypeDTO> getAllDegreeTypes() {
		return degreeTypeRepository.findAll().stream()
				.map(degreeTypeMapper::entityToDTO)
				.collect(Collectors.toList());
	}
}
