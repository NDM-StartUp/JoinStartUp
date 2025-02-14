package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetApplicationDTO;
import ndmstartup.joinstartup.Mappers.ApplicationMapper;
import ndmstartup.joinstartup.Models.ApplicationCv;
import ndmstartup.joinstartup.Repositories.ApplicationRepository;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Repositories.StartUpRepository;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
	private final EmployeeRepository employeeRepository;
	private final ApplicationRepository applicationRepository;
	private final StartUpRepository startUpRepository;
	private final ApplicationMapper applicationMapper;

	@Override
	public List<GetApplicationDTO> getApplicationsByStartUpAndCriteria(String applicationStatus, LocalDateTime startDate, LocalDateTime endDate, Long startUpId) {
		startUpRepository.findById(startUpId).orElseThrow(() -> new NoSuchElementException("Start up not found with id" + startUpId));

		List<ApplicationCv> applications;

		if (applicationStatus != null && startDate != null && endDate != null) {
			applications = applicationRepository.findByStartupIdAndStatusAndDatePeriod(applicationStatus, startDate, endDate, startUpId);
		} else if (applicationStatus != null) {
			applications = applicationRepository.findByStartupIdAndStatus(applicationStatus, startUpId);
		} else if (startDate != null && endDate != null) {
			applications = applicationRepository.findByStartupIdAndDatePeriod(startDate, endDate, startUpId);
		} else {
			applications = applicationRepository.findAll();
		}

		return applications.stream()
				.map(applicationMapper::entityToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public void deleteApplicationByApplicationId(Long applicationId, Long employeeId) {
		ApplicationCv applicationCv = applicationRepository.findById(applicationId).orElseThrow(() -> new NoSuchElementException("Application not found with id" + applicationId));
		if (!Objects.equals(applicationCv.getEmployeeCv().getEmployee().getId(), employeeId)) {
			throw new NoSuchElementException("Employee with id " + employeeId + " does not belong to application with id " + applicationId);
		}
		applicationRepository.deleteById(applicationId);
	}

	@Override
	public List<GetApplicationDTO> getApplicationsByEmployeeAndCriteria(String applicationStatus, LocalDateTime startDate, LocalDateTime endDate, Long employeeId) {
		employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found with id" + employeeId));

		List<ApplicationCv> applications;

		if (applicationStatus != null && startDate != null && endDate != null) {
			applications = applicationRepository.findByEmployeeIdAndStatusAndDatePeriod(applicationStatus, startDate, endDate, employeeId);
		} else if (applicationStatus != null) {
			applications = applicationRepository.findByEmployeeIdAndStatus(applicationStatus, employeeId);
		} else if (startDate != null && endDate != null) {
			applications = applicationRepository.findByEmployeeIdAndDatePeriod(startDate, endDate, employeeId);
		} else {
			applications = applicationRepository.findAll();
		}

		return applications.stream()
				.map(applicationMapper::entityToDTO)
				.collect(Collectors.toList());
	}
}
