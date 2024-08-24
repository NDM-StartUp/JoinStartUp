package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
//import ndmstartup.joinstartup.DTOs.GetStartUpCompanyNameEmployeeDTO;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Mappers.StartUpMapper;
import ndmstartup.joinstartup.Models.StartUp;
import ndmstartup.joinstartup.Repositories.StartUpRepository;
import ndmstartup.joinstartup.Services.Interfaces.StartUpService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StartUpServiceImpl implements StartUpService {
	private final StartUpMapper startUpMapper;
	private final StartUpRepository startUpRepository;

	@Override
	public List<GetStartUpDTO> getAllStartUps() {
		List<StartUp> startUps = startUpRepository.findAll();
		return startUps.stream().map(startUpMapper::entityToDTO).toList();
	}

	@Override
	public GetStartUpDTO getStartUpByStartUpId(Long startUpId) {
		StartUp startUp = startUpRepository
				.findById(startUpId)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with id:" + startUpId));
		return startUpMapper.entityToDTO(startUp);
	}

	@Override
	public GetStartUpIdEmployeeDTO getEmployeesByStartUpId(Long startUpId) {
		StartUp startUp = startUpRepository.findById(startUpId)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with id:" + startUpId));
		return startUpMapper.entityToStartUpIdEmployeeDTO(startUp);
	}

	@Override
	public GetStartUpCompanyNameEmployeeDTO getEmployeesByStartUpCompanyName(String companyName) {
		StartUp startUp = startUpRepository.findByCompanyName(companyName)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with company name:" + companyName));
		return startUpMapper.entityToStartUpCompanyNameEmployeeDTO(startUp);
	}

	@Override
	public GetStartUpLocationEmployeeDTO getEmployeesByStartUpLocation(String location) {
		StartUp startUp = startUpRepository.findByLocation(location)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with location:" + location));
		return startUpMapper.entityToStartUpLocationEmployeeDTO(startUp);
	}

	@Override
	public GetStartUpIsPaidEmployeeDTO getEmployeesByStartUpIsPaid(boolean isPaid) {
		StartUp startUp = startUpRepository.findByIsPaid(isPaid)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with isPaid:" + isPaid));
		return startUpMapper.entityToStartUpIsPaidEmployeeDTO(startUp);
	}
}
