package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetMajorDTO;
import ndmstartup.joinstartup.Mappers.MajorMapper;
import ndmstartup.joinstartup.Repositories.MajorRepository;
import ndmstartup.joinstartup.Services.Interfaces.MajorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements MajorService {
	private final MajorRepository majorRepository;
	private final MajorMapper majorMapper;

	@Override
	@Transactional
	public List<GetMajorDTO> getAllMajors() {
		return majorRepository.findAll().stream()
				.map(majorMapper::entityToDTO)
				.collect(Collectors.toList());
	}
}
