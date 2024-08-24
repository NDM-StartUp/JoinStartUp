package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetApplicationDTO;
import ndmstartup.joinstartup.Mappers.ApplicationMapper;
import ndmstartup.joinstartup.Models.ApplicationCv;
import ndmstartup.joinstartup.Repositories.ApplicationRepository;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	private final ApplicationRepository applicationRepository;
	private final ApplicationMapper applicationMapper;

	@Override
	public List<GetApplicationDTO> getApplicationsByCriteria(String startupStatus, Date startDate, Date endDate) {
		List<ApplicationCv> applications;

		if (startupStatus != null && startDate != null && endDate != null) {
			applications = applicationRepository.findByStartupStatusAndDatePeriod(startupStatus, startDate, endDate);
		} else if (startupStatus != null) {
			applications = applicationRepository.findByStartupStatus(startupStatus);
		} else if (startDate != null && endDate != null) {
			applications = applicationRepository.findByDatePeriod(startDate, endDate);
		} else {
			applications = applicationRepository.findAll();
		}

		return applications.stream()
				.map(applicationMapper::entityToDTO)
				.collect(Collectors.toList());
	}
}
