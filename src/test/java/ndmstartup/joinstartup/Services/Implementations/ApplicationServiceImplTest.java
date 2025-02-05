package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetApplicationDTO;
import ndmstartup.joinstartup.Mappers.ApplicationMapper;
import ndmstartup.joinstartup.Models.ApplicationCv;
import ndmstartup.joinstartup.Models.StartUp;
import ndmstartup.joinstartup.Models.EmployeeCv;
import ndmstartup.joinstartup.Models.Employee;
import ndmstartup.joinstartup.Repositories.ApplicationRepository;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Repositories.StartUpRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private StartUpRepository startUpRepository;

	@Mock
	private ApplicationMapper applicationMapper;

	@InjectMocks
	private ApplicationServiceImpl applicationService;

	private ApplicationCv applicationCv;
	private StartUp startUp;
	private EmployeeCv employeeCv;
	private Employee employee;

	@BeforeEach
	void setUp() {
		employee = new Employee();
		employee.setId(1L);

		employeeCv = new EmployeeCv();
		employeeCv.setId(1L);
		employeeCv.setEmployee(employee);

		startUp = new StartUp();
		startUp.setId(1L);

		applicationCv = new ApplicationCv();
		applicationCv.setId(1L);
		applicationCv.setDate(LocalDateTime.now());
		applicationCv.setEmployeeCv(employeeCv);
		applicationCv.setStartUp(startUp);
	}

	@Test
	void getApplicationsByStartUpAndCriteria_ReturnsApplications_WhenCriteriaMatched() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now();
		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.of(startUp));
		Mockito.when(applicationRepository.findByStartupIdAndStatusAndDatePeriod("Pending", startDate, endDate, 1L))
				.thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, LocalDateTime.now(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByStartUpAndCriteria("Pending", startDate, endDate, 1L);

		Assertions.assertEquals(1, result.size());
		Mockito.verify(startUpRepository).findById(1L);
		Mockito.verify(applicationRepository).findByStartupIdAndStatusAndDatePeriod("Pending", startDate, endDate, 1L);
	}

	@Test
	void getApplicationsByStartUpAndCriteria_ReturnsApplications_WhenOnlyStatusProvided() {
		String applicationStatus = "Pending";
		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.of(startUp));
		Mockito.when(applicationRepository.findByStartupIdAndStatus(applicationStatus, 1L)).thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, applicationCv.getDate(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByStartUpAndCriteria(applicationStatus, null, null, 1L);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(1L, result.get(0).getId());
		Mockito.verify(startUpRepository).findById(1L);
		Mockito.verify(applicationRepository).findByStartupIdAndStatus(applicationStatus, 1L);
	}
	@Test
	void getApplicationsByStartUpAndCriteria_ReturnsApplications_WhenOnlyDateRangeProvided() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now();
		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.of(startUp));
		Mockito.when(applicationRepository.findByStartupIdAndDatePeriod(startDate, endDate, 1L)).thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, applicationCv.getDate(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByStartUpAndCriteria(null, startDate, endDate, 1L);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(1L, result.get(0).getId());
		Mockito.verify(startUpRepository).findById(1L);
		Mockito.verify(applicationRepository).findByStartupIdAndDatePeriod(startDate, endDate, 1L);
	}

	@Test
	void getApplicationsByStartUpAndCriteria_ReturnsAllApplications_WhenNoCriteriaProvided() {
		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.of(startUp));
		Mockito.when(applicationRepository.findAll()).thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, applicationCv.getDate(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByStartUpAndCriteria(null, null, null, 1L);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(1L, result.get(0).getId());
		Mockito.verify(startUpRepository).findById(1L);
		Mockito.verify(applicationRepository).findAll();
	}

	@Test
	void getApplicationsByStartUpAndCriteria_ThrowsException_WhenStartUpNotFound() {
		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.empty());

		Assertions.assertThrows(NoSuchElementException.class, () ->
				applicationService.getApplicationsByStartUpAndCriteria("Pending", LocalDateTime.now().minusDays(1), LocalDateTime.now(), 1L));

		Mockito.verify(startUpRepository).findById(1L);
	}

	@Test
	void deleteApplicationByApplicationId_DeletesApplicationSuccessfully() {
		Mockito.when(applicationRepository.findById(1L)).thenReturn(Optional.of(applicationCv));

		applicationService.deleteApplicationByApplicationId(1L, 1L);

		Mockito.verify(applicationRepository).findById(1L);
		Mockito.verify(applicationRepository).deleteById(1L);
	}

	@Test
	void deleteApplicationByApplicationId_ThrowsException_WhenApplicationNotFound() {
		Mockito.when(applicationRepository.findById(1L)).thenReturn(Optional.empty());

		Assertions.assertThrows(NoSuchElementException.class, () ->
				applicationService.deleteApplicationByApplicationId(1L, 1L));

		Mockito.verify(applicationRepository).findById(1L);
	}

	@Test
	void deleteApplicationByApplicationId_ThrowsException_WhenEmployeeMismatch() {
		Employee anotherEmployee = new Employee();
		anotherEmployee.setId(2L);
		employeeCv.setEmployee(anotherEmployee);

		Mockito.when(applicationRepository.findById(1L)).thenReturn(Optional.of(applicationCv));

		Assertions.assertThrows(NoSuchElementException.class, () ->
				applicationService.deleteApplicationByApplicationId(1L, 1L));

		Mockito.verify(applicationRepository).findById(1L);
	}

	@Test
	void getApplicationsByEmployeeAndCriteria_ReturnsApplications_WhenCriteriaMatched() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now();
		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(applicationRepository.findByEmployeeIdAndStatusAndDatePeriod("Pending", startDate, endDate, 1L))
				.thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, LocalDateTime.now(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByEmployeeAndCriteria("Pending", startDate, endDate, 1L);

		Assertions.assertEquals(1, result.size());
		Mockito.verify(employeeRepository).findById(1L);
		Mockito.verify(applicationRepository).findByEmployeeIdAndStatusAndDatePeriod("Pending", startDate, endDate, 1L);
	}

	@Test
	void getApplicationsByEmployeeAndCriteria_ReturnsApplications_WhenOnlyStatusProvided() {
		String applicationStatus = "Pending";
		Long employeeId = 1L;

		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
		Mockito.when(applicationRepository.findByEmployeeIdAndStatus(applicationStatus, employeeId))
				.thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, applicationCv.getDate(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByEmployeeAndCriteria(applicationStatus, null, null, employeeId);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(1L, result.get(0).getId());
		Mockito.verify(employeeRepository).findById(employeeId);
		Mockito.verify(applicationRepository).findByEmployeeIdAndStatus(applicationStatus, employeeId);
	}

	@Test
	void getApplicationsByEmployeeAndCriteria_ReturnsApplications_WhenOnlyDateRangeProvided() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(2);
		LocalDateTime endDate = LocalDateTime.now().minusDays(1);
		Long employeeId = 1L;

		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
		Mockito.when(applicationRepository.findByEmployeeIdAndDatePeriod(startDate, endDate, employeeId))
				.thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, applicationCv.getDate(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByEmployeeAndCriteria(null, startDate, endDate, employeeId);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(1L, result.get(0).getId());
		Mockito.verify(employeeRepository).findById(employeeId);
		Mockito.verify(applicationRepository).findByEmployeeIdAndDatePeriod(startDate, endDate, employeeId);
	}

	@Test
	void getApplicationsByEmployeeAndCriteria_ReturnsAllApplications_WhenNoCriteriaProvided() {
		Long employeeId = 1L;

		List<ApplicationCv> applications = List.of(applicationCv);

		Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
		Mockito.when(applicationRepository.findAll()).thenReturn(applications);
		Mockito.when(applicationMapper.entityToDTO(applicationCv))
				.thenReturn(new GetApplicationDTO(1L, applicationCv.getDate(), null, null));

		List<GetApplicationDTO> result = applicationService.getApplicationsByEmployeeAndCriteria(null, null, null, employeeId);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals(1L, result.get(0).getId());
		Mockito.verify(employeeRepository).findById(employeeId);
		Mockito.verify(applicationRepository).findAll();
	}
	@Test
	void getApplicationsByEmployeeAndCriteria_ThrowsException_WhenEmployeeNotFound() {
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

		Assertions.assertThrows(NoSuchElementException.class, () ->
				applicationService.getApplicationsByEmployeeAndCriteria("Pending", LocalDateTime.now().minusDays(1), LocalDateTime.now(), 1L));

		Mockito.verify(employeeRepository).findById(1L);
	}
}
