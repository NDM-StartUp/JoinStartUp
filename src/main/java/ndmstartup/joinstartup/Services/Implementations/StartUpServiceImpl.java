package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
//import ndmstartup.joinstartup.DTOs.GetStartUpCompanyNameEmployeeDTO;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Mappers.StartUpMapper;
import ndmstartup.joinstartup.Models.ProgressStatus;
import ndmstartup.joinstartup.Models.StartUp;
import ndmstartup.joinstartup.Models.StartUpStatus;
import ndmstartup.joinstartup.Repositories.ProgressStatusRepository;
import ndmstartup.joinstartup.Repositories.StartUpRepository;
import ndmstartup.joinstartup.Repositories.StartUpStatusRepository;
import ndmstartup.joinstartup.Services.Interfaces.StartUpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StartUpServiceImpl implements StartUpService {
	private final StartUpMapper startUpMapper;
	private final StartUpRepository startUpRepository;
	private final ProgressStatusRepository progressStatusRepository;
	private final StartUpStatusRepository startUpStatusRepository;

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
	public List<GetStartUpDTO> searchStartUpByCriteria(Long startUpId, String companyName, String location, Boolean isPaid) {
		List<StartUp> startUps;

		if (startUpId != null) {
			startUps = startUpRepository.findByIdOrCompanyNameOrLocationOrIsPaid(startUpId, companyName, location, isPaid);
		} else {
			startUps = startUpRepository.findByCriteria(companyName, location, isPaid);
		}

		return startUps.stream().map(startUpMapper::entityToDTO).collect(Collectors.toList());
	}


	@Override
	public GetStartUpIdEmployeeDTO getEmployeesByStartUpId(Long startUpId) {
		StartUp startUp = startUpRepository.findById(startUpId)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with id:" + startUpId));
		return startUpMapper.entityToStartUpIdEmployeeDTO(startUp);
	}

	@Override
	@Transactional
	public void addStartUp (PostStartUpDTO postStartUpDTO){
		StartUp startUp = startUpMapper.DTOToEntity(postStartUpDTO);
		startUpRepository.save(startUp);
	}

	@Override
	@Transactional
	public void updateStartUp(Long startUpId, PostStartUpDTO postStartUpDTO) {
		StartUp existingStartUp = startUpRepository.findById(startUpId)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with id:" + startUpId));

		existingStartUp.setName(postStartUpDTO.getName());
		existingStartUp.setCompanyName(postStartUpDTO.getCompanyName());
		existingStartUp.setDescription(postStartUpDTO.getDescription());
		existingStartUp.setRequirements(postStartUpDTO.getRequirements());
		existingStartUp.setLocation(postStartUpDTO.getLocation());
		existingStartUp.setPaid(postStartUpDTO.isPaid());

		startUpRepository.save(existingStartUp);
	}

	@Override
	@Transactional
	public void deleteStartUp(Long startUpId) {
		StartUp existingStartUp = startUpRepository.findById(startUpId)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with id:" + startUpId));

		startUpRepository.delete(existingStartUp);
	}

	@Override
	public List<GetStartUpDTO> getStartUpsByProgressStatus(String progressStatusName) {
		List<StartUp> startUps = startUpRepository.findByProgressStatus(progressStatusName);
		return startUps.stream()
				.map(startUpMapper::entityToDTO)
				.collect(Collectors.toList());
	}


	@Transactional
	@Override
	public void updateStartUpStatus(Long startUpId, String progressStatusName) {
		StartUp startUp = startUpRepository.findById(startUpId)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with id: " + startUpId));

		ProgressStatus progressStatus = progressStatusRepository.findByName(progressStatusName)
				.orElseThrow(() -> new NoSuchElementException("ProgressStatus not found with name: " + progressStatusName));

		StartUpStatus existingStartUpStatus = startUpStatusRepository.findByStartUpId(startUpId)
				.orElseThrow(() -> new NoSuchElementException("No status found for the StartUp with id" + startUpId));


		existingStartUpStatus.setProgressStatus(progressStatus);
		existingStartUpStatus.setDate(LocalDate.now());

		startUpStatusRepository.save(existingStartUpStatus);
	}

	@Transactional
	@Override
	public void deleteStartUpSkills(Long startUpId) {
		StartUp startUp = startUpRepository.findById(startUpId)
				.orElseThrow(() -> new NoSuchElementException("StartUp not found with id: " + startUpId));

		startUp.getSkills().clear();

		startUpRepository.save(startUp);
	}

	@Transactional
	@Override
	public List<GetStartUpStatusDTO> getAllStartUpProgressStatuses(Long startUpId) {
		List<StartUpStatus> statuses = startUpStatusRepository.findAllByStartUpId(startUpId);
		return statuses.stream().map(startUpMapper::entityToStartUpStatusDTO).collect(Collectors.toList());
	}

/*	@Override
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
	}*/
}
