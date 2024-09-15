package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetUniversityDTO;
import ndmstartup.joinstartup.Mappers.UniversityMapper;
import ndmstartup.joinstartup.Repositories.UniversityRepository;
import ndmstartup.joinstartup.Services.Interfaces.UniversityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
	private final UniversityRepository universityRepository;
	private final UniversityMapper universityMapper;

	@Override
	@Transactional
	public List<GetUniversityDTO> getAllUniversities() {
		return universityRepository.findAll().stream()
				.map(universityMapper::entityToDTO)
				.collect(Collectors.toList());
	}
}
