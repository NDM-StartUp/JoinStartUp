package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.DTOs.GetStartUpDTO;
import ndmstartup.joinstartup.DTOs.GetStartUpStatusDTO;
import ndmstartup.joinstartup.DTOs.PostStartUpDTO;
import ndmstartup.joinstartup.Mappers.StartUpMapper;
import ndmstartup.joinstartup.Models.ProgressStatus;
import ndmstartup.joinstartup.Models.Skill;
import ndmstartup.joinstartup.Models.StartUp;
import ndmstartup.joinstartup.Models.StartUpStatus;
import ndmstartup.joinstartup.Repositories.ProgressStatusRepository;
import ndmstartup.joinstartup.Repositories.StartUpRepository;
import ndmstartup.joinstartup.Repositories.StartUpStatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.*;


@ExtendWith(MockitoExtension.class)
public class StartUpServiceImplTest {
	@Mock
	private StartUpMapper startUpMapper;
	@Mock
	private StartUpRepository startUpRepository;
	@Mock
	private ProgressStatusRepository progressStatusRepository;
	@Mock
	private StartUpStatusRepository startUpStatusRepository;

	@InjectMocks
	private StartUpServiceImpl startUpService;

	private StartUp startUp1;
	private StartUp startUp2;
	private ProgressStatus progressStatus;
	private StartUpStatus startUpStatus;

	@BeforeEach
	void setUp() {
		startUp1 = new StartUp();
		startUp1.setId(1L);
		startUp1.setName("Developer");
		startUp1.setCompanyName("TechCo");
		startUp1.setLocation("New York");
		startUp1.setRequirements("Experience with Java and Spring");
		startUp1.setDescription("Exciting startup looking for a developer.");
		startUp1.setPaid(true);

		startUp2 = new StartUp();
		startUp2.setId(2L);
		startUp2.setName("Data Scientist");
		startUp2.setCompanyName("InnovateX");
		startUp2.setLocation("San Francisco");
		startUp2.setRequirements("Knowledge of Machine Learning");
		startUp2.setDescription("Innovative startup seeking a data scientist.");
		startUp2.setPaid(false);

		progressStatus = new ProgressStatus();
		progressStatus.setId(1L);
		progressStatus.setName("In Progress");

		startUpStatus = new StartUpStatus();
		startUpStatus.setStartUp(startUp1);
		startUpStatus.setProgressStatus(progressStatus);
	}

	@Test
	void getAllStartUps_ReturnsStartUps(){
		List<StartUp> startUps = Arrays.asList(startUp1,startUp2);
		Mockito.when(startUpRepository.findAll()).thenReturn(startUps);
		Mockito.when(startUpMapper.entityToDTO(startUp1)).thenReturn(new GetStartUpDTO(1L, "Developer", "TechCo", "Exciting startup looking for a developer.","Experience with Java and Spring","New York", true));
		Mockito.when(startUpMapper.entityToDTO(startUp2)).thenReturn(new GetStartUpDTO(2L, "Data Science", "InnovateX", "Innovative startup seeking a data scientist.","Knowledge of Machine Learning","San Francisco", false));

		List<GetStartUpDTO> result = startUpService.getAllStartUps();

		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("Developer", result.get(0).getName());
		Assertions.assertEquals("Data Science", result.get(1).getName());

		Mockito.verify(startUpRepository).findAll();
		Mockito.verify(startUpMapper, Mockito.times(2)).entityToDTO(Mockito.any(StartUp.class));
	}

	@Test
	void getAllStartUps_ReturnEmptyList(){
		Mockito.when(startUpRepository.findAll()).thenReturn(Collections.emptyList());

		List<GetStartUpDTO> result = startUpService.getAllStartUps();

		Assertions.assertTrue(result.isEmpty());
		Mockito.verify(startUpRepository).findAll();
		Mockito.verify(startUpMapper, Mockito.never()).entityToDTO(Mockito.any(StartUp.class));
	}

	@Test
	void getStartUpByStartUpId_ReturnsStartUp() {
		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.of(startUp1));
		Mockito.when(startUpMapper.entityToDTO(startUp1))
				.thenReturn(new GetStartUpDTO(1L, "Developer", "TechCo", "Exciting startup looking for a developer.",
						"Experience with Java and Spring", "New York", true));

		GetStartUpDTO result = startUpService.getStartUpByStartUpId(1L);

		Assertions.assertEquals("Developer", result.getName());
		Assertions.assertEquals("TechCo", result.getCompanyName());

		Mockito.verify(startUpRepository).findById(1L);
		Mockito.verify(startUpMapper).entityToDTO(startUp1);
	}

	@Test
	void getStartUpByStartUpId_ThrowsException_WhenNotFound() {
		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.empty());

		NoSuchElementException exception = Assertions.assertThrows(
				NoSuchElementException.class, () -> startUpService.getStartUpByStartUpId(1L));

		Assertions.assertEquals("StartUp not found with id:1", exception.getMessage());
		Mockito.verify(startUpRepository).findById(1L);
	}


	@Test
	void searchStartUpByCriteria_WithStartUpId() {
		Long startUpId = 1L;
		Mockito.when(startUpRepository.findByIdOrCompanyNameOrLocationOrIsPaid(startUpId, null, null, null))
				.thenReturn(List.of(startUp1));
		Mockito.when(startUpMapper.entityToDTO(startUp1))
				.thenReturn(new GetStartUpDTO(1L, "Developer", "TechCo", "Exciting startup looking for a developer.",
						"Experience with Java and Spring", "New York", true));

		List<GetStartUpDTO> result = startUpService.searchStartUpByCriteria(startUpId, null, null, null);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Developer", result.get(0).getName());
		Mockito.verify(startUpRepository).findByIdOrCompanyNameOrLocationOrIsPaid(startUpId, null, null, null);
	}

	@Test
	void searchStartUpByCriteria_WithCompanyName() {
		String companyName = "TechCo";
		Mockito.when(startUpRepository.findByCriteria(companyName, null, null))
				.thenReturn(List.of(startUp1));
		Mockito.when(startUpMapper.entityToDTO(startUp1))
				.thenReturn(new GetStartUpDTO(1L, "Developer", "TechCo", "Exciting startup looking for a developer.",
						"Experience with Java and Spring", "New York", true));

		List<GetStartUpDTO> result = startUpService.searchStartUpByCriteria(null, companyName, null, null);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Developer", result.get(0).getName());
		Mockito.verify(startUpRepository).findByCriteria(companyName, null, null);
	}

	@Test
	void searchStartUpByCriteria_WithLocation() {
		String location = "San Francisco";
		Mockito.when(startUpRepository.findByCriteria(null, location, null))
				.thenReturn(List.of(startUp2));
		Mockito.when(startUpMapper.entityToDTO(startUp2))
				.thenReturn(new GetStartUpDTO(2L, "Data Scientist", "InnovateX", "Innovative startup seeking a data scientist.",
						"Knowledge of Machine Learning", "San Francisco", false));

		List<GetStartUpDTO> result = startUpService.searchStartUpByCriteria(null, null, location, null);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Data Scientist", result.get(0).getName());
		Mockito.verify(startUpRepository).findByCriteria(null, location, null);
	}

	@Test
	void searchStartUpByCriteria_WithIsPaid() {
		Boolean isPaid = true;
		Mockito.when(startUpRepository.findByCriteria(null, null, isPaid))
				.thenReturn(List.of(startUp1));
		Mockito.when(startUpMapper.entityToDTO(startUp1))
				.thenReturn(new GetStartUpDTO(1L, "Developer", "TechCo", "Exciting startup looking for a developer.",
						"Experience with Java and Spring", "New York", true));

		List<GetStartUpDTO> result = startUpService.searchStartUpByCriteria(null, null, null, isPaid);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Developer", result.get(0).getName());
		Mockito.verify(startUpRepository).findByCriteria(null, null, isPaid);
	}

	@Test
	void searchStartUpByCriteria_WithAllParameters() {
		Long startUpId = 1L;
		String companyName = "TechCo";
		String location = "New York";
		Boolean isPaid = true;

		Mockito.when(startUpRepository.findByIdOrCompanyNameOrLocationOrIsPaid(startUpId, companyName, location, isPaid))
				.thenReturn(List.of(startUp1));
		Mockito.when(startUpMapper.entityToDTO(startUp1))
				.thenReturn(new GetStartUpDTO(1L, "Developer", "TechCo", "Exciting startup looking for a developer.",
						"Experience with Java and Spring", "New York", true));

		List<GetStartUpDTO> result = startUpService.searchStartUpByCriteria(startUpId, companyName, location, isPaid);

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Developer", result.get(0).getName());
		Mockito.verify(startUpRepository).findByIdOrCompanyNameOrLocationOrIsPaid(startUpId, companyName, location, isPaid);
	}

	@Test
	void searchStartUpByCriteria_EmptyResult() {
		Mockito.when(startUpRepository.findByCriteria("NonExistent", null, null))
				.thenReturn(List.of());

		List<GetStartUpDTO> result = startUpService.searchStartUpByCriteria(null, "NonExistent", null, null);

		Assertions.assertTrue(result.isEmpty());
		Mockito.verify(startUpRepository).findByCriteria("NonExistent", null, null);
	}

	@Test
	void addStartUp_SavesStartUpSuccessfully() {
		PostStartUpDTO dto = new PostStartUpDTO("Developer", "TechCo", "New York", "Experience with Java",
				"Exciting startup looking for a developer.", true);
		StartUp newStartUp = new StartUp();

		Mockito.when(startUpMapper.DTOToEntity(dto)).thenReturn(newStartUp);

		startUpService.addStartUp(dto);

		Mockito.verify(startUpRepository).save(newStartUp);
	}


	@Test
	void updateStartUp_UpdatesSuccessfully_WhenStartUpExists() {
		Long startUpId = 1L;
		PostStartUpDTO dto = new PostStartUpDTO("Updated Developer", "Updated TechCo", "Updated Description", "Updated Requirements", "Updated New York", true);
		StartUp existingStartUp = new StartUp();
		existingStartUp.setId(startUpId);

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.of(existingStartUp));

		startUpService.updateStartUp(startUpId, dto);

		Assertions.assertEquals("Updated Developer", existingStartUp.getName());
		Assertions.assertEquals("Updated TechCo", existingStartUp.getCompanyName());
		Assertions.assertEquals("Updated New York", existingStartUp.getLocation());
		Assertions.assertEquals("Updated Requirements", existingStartUp.getRequirements());
		Assertions.assertEquals("Updated Description", existingStartUp.getDescription());
		Assertions.assertTrue(existingStartUp.isPaid());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(startUpRepository).save(existingStartUp);
	}

	@Test
	void updateStartUp_ThrowsException_WhenStartUpDoesNotExist() {
		Long startUpId = 1L;
		PostStartUpDTO dto = new PostStartUpDTO("Updated Developer", "Updated TechCo", "Updated Description", "Updated Requirements", "Updated New York", true);

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.empty());

		NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class,
				() -> startUpService.updateStartUp(startUpId, dto));

		Assertions.assertEquals("StartUp not found with id:" + startUpId, exception.getMessage());
		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(startUpRepository, Mockito.never()).save(Mockito.any());
	}

	@Test
	void deleteStartUp_DeletesSuccessfully() {
		Mockito.when(startUpRepository.findById(1L)).thenReturn(Optional.of(startUp1));

		startUpService.deleteStartUp(1L);

		Mockito.verify(startUpRepository).delete(startUp1);
	}


	@Test
	void deleteStartUp_ThrowsException_WhenStartUpDoesNotExist() {
		Long startUpId = 1L;
		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.empty());

		NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class,
				() -> startUpService.deleteStartUp(startUpId));

		Assertions.assertEquals("StartUp not found with id:" + startUpId, exception.getMessage());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(startUpRepository, Mockito.never()).delete(Mockito.any());
	}

	@Test
	void getStartUpsByProgressStatus_ReturnsStartUps() {
		List<StartUp> startUps = Arrays.asList(startUp1);
		Mockito.when(startUpRepository.findByProgressStatus("In Progress")).thenReturn(startUps);
		Mockito.when(startUpMapper.entityToDTO(startUp1))
				.thenReturn(new GetStartUpDTO(1L, "Developer", "TechCo", "Exciting startup looking for a developer.",
						"Experience with Java and Spring", "New York", true));

		List<GetStartUpDTO> result = startUpService.getStartUpsByProgressStatus("In Progress");

		Assertions.assertEquals(1, result.size());
		Assertions.assertEquals("Developer", result.get(0).getName());
		Mockito.verify(startUpRepository).findByProgressStatus("In Progress");
	}

	@Test
	void getStartUpsByProgressStatus_ReturnsEmptyList_WhenNoStartUpsMatch() {
		Mockito.when(startUpRepository.findByProgressStatus("Nonexistent Status")).thenReturn(Collections.emptyList());

		List<GetStartUpDTO> result = startUpService.getStartUpsByProgressStatus("Nonexistent Status");

		Assertions.assertTrue(result.isEmpty());
		Mockito.verify(startUpRepository).findByProgressStatus("Nonexistent Status");
		Mockito.verify(startUpMapper, Mockito.never()).entityToDTO(Mockito.any());
	}

	@Test
	void updateStartUpStatus_UpdatesSuccessfully() {
		Long startUpId = 1L;
		String progressStatusName = "In Progress";

		StartUp startUp = new StartUp();
		startUp.setId(startUpId);

		ProgressStatus progressStatus = new ProgressStatus();
		progressStatus.setName(progressStatusName);

		StartUpStatus startUpStatus = new StartUpStatus();
		startUpStatus.setStartUp(startUp);

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.of(startUp));
		Mockito.when(progressStatusRepository.findByName(progressStatusName)).thenReturn(Optional.of(progressStatus));
		Mockito.when(startUpStatusRepository.findByStartUpId(startUpId)).thenReturn(Optional.of(startUpStatus));

		startUpService.updateStartUpStatus(startUpId, progressStatusName);

		Assertions.assertEquals(progressStatus, startUpStatus.getProgressStatus());
		Assertions.assertEquals(LocalDate.now(), startUpStatus.getDate());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(progressStatusRepository).findByName(progressStatusName);
		Mockito.verify(startUpStatusRepository).findByStartUpId(startUpId);
		Mockito.verify(startUpStatusRepository).save(startUpStatus);
	}

	@Test
	void updateStartUpStatus_ThrowsException_WhenStartUpNotFound() {
		Long startUpId = 1L;
		String progressStatusName = "In Progress";

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.empty());

		NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class,
				() -> startUpService.updateStartUpStatus(startUpId, progressStatusName));

		Assertions.assertEquals("StartUp not found with id: " + startUpId, exception.getMessage());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verifyNoInteractions(progressStatusRepository);
		Mockito.verifyNoInteractions(startUpStatusRepository);
	}

	@Test
	void updateStartUpStatus_ThrowsException_WhenProgressStatusNotFound() {
		Long startUpId = 1L;
		String progressStatusName = "Nonexistent";

		StartUp startUp = new StartUp();
		startUp.setId(startUpId);

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.of(startUp));
		Mockito.when(progressStatusRepository.findByName(progressStatusName)).thenReturn(Optional.empty());

		NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class,
				() -> startUpService.updateStartUpStatus(startUpId, progressStatusName));

		Assertions.assertEquals("ProgressStatus not found with name: " + progressStatusName, exception.getMessage());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(progressStatusRepository).findByName(progressStatusName);
		Mockito.verifyNoInteractions(startUpStatusRepository);
	}

	@Test
	void updateStartUpStatus_ThrowsException_WhenStartUpStatusNotFound() {
		Long startUpId = 1L;
		String progressStatusName = "In Progress";

		StartUp startUp = new StartUp();
		startUp.setId(startUpId);

		ProgressStatus progressStatus = new ProgressStatus();
		progressStatus.setName(progressStatusName);

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.of(startUp));
		Mockito.when(progressStatusRepository.findByName(progressStatusName)).thenReturn(Optional.of(progressStatus));
		Mockito.when(startUpStatusRepository.findByStartUpId(startUpId)).thenReturn(Optional.empty());

		NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class,
				() -> startUpService.updateStartUpStatus(startUpId, progressStatusName));

		Assertions.assertEquals("No status found for the StartUp with id" + startUpId, exception.getMessage());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(progressStatusRepository).findByName(progressStatusName);
		Mockito.verify(startUpStatusRepository).findByStartUpId(startUpId);
		Mockito.verifyNoMoreInteractions(startUpStatusRepository);
	}

	@Test
	void deleteStartUpSkills_ClearsSkillsSuccessfully_WhenStartUpExists() {
		Long startUpId = 1L;

		StartUp startUp = new StartUp();
		startUp.setId(startUpId);

		Skill skill1 = new Skill();
		skill1.setId(1L);
		skill1.setName("Java");

		Skill skill2 = new Skill();
		skill2.setId(2L);
		skill2.setName("Python");

		startUp.setSkills(new ArrayList<>(Arrays.asList(skill1, skill2)));

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.of(startUp));

		startUpService.deleteStartUpSkills(startUpId);

		Assertions.assertTrue(startUp.getSkills().isEmpty());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(startUpRepository).save(startUp);
	}

	@Test
	void deleteStartUpSkills_ThrowsException_WhenStartUpDoesNotExist() {
		Long startUpId = 1L;

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.empty());

		NoSuchElementException exception = Assertions.assertThrows(
				NoSuchElementException.class,
				() -> startUpService.deleteStartUpSkills(startUpId)
		);

		Assertions.assertEquals("StartUp not found with id: " + startUpId, exception.getMessage());

		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(startUpRepository, Mockito.never()).save(Mockito.any());
	}

	@Test
	void deleteStartUpSkills_DoesNotModifySkills_WhenSkillsAreAlreadyEmpty() {
		Long startUpId = 1L;

		StartUp startUp = new StartUp();
		startUp.setId(startUpId);
		startUp.setSkills(new ArrayList<>());

		Mockito.when(startUpRepository.findById(startUpId)).thenReturn(Optional.of(startUp));

		startUpService.deleteStartUpSkills(startUpId);

		Assertions.assertTrue(startUp.getSkills().isEmpty());
		Mockito.verify(startUpRepository).findById(startUpId);
		Mockito.verify(startUpRepository).save(startUp);
	}

	@Test
	void getAllStartUpProgressStatuses_ReturnsProgressStatuses_WhenStartUpHasStatuses() {
		Long startUpId = 1L;

		ProgressStatus progressStatus1 = new ProgressStatus();
		progressStatus1.setId(1L);
		progressStatus1.setName("In Progress");

		ProgressStatus progressStatus2 = new ProgressStatus();
		progressStatus2.setId(2L);
		progressStatus2.setName("Completed");

		StartUpStatus status1 = new StartUpStatus();
		status1.setId(1L);
		status1.setProgressStatus(progressStatus1);
		status1.setDate(LocalDate.of(2023, 10, 1));

		StartUpStatus status2 = new StartUpStatus();
		status2.setId(2L);
		status2.setProgressStatus(progressStatus2);
		status2.setDate(LocalDate.of(2023, 10, 15));

		List<StartUpStatus> statuses = Arrays.asList(status1, status2);

		Mockito.when(startUpStatusRepository.findAllByStartUpId(startUpId)).thenReturn(statuses);
		Mockito.when(startUpMapper.entityToStartUpStatusDTO(status1))
				.thenReturn(GetStartUpStatusDTO.builder().statusName("In Progress").date(LocalDate.of(2023, 10, 1)).build());
		Mockito.when(startUpMapper.entityToStartUpStatusDTO(status2))
				.thenReturn(GetStartUpStatusDTO.builder().statusName("Completed").date(LocalDate.of(2023, 10, 15)).build());

		List<GetStartUpStatusDTO> result = startUpService.getAllStartUpProgressStatuses(startUpId);

		Assertions.assertEquals(2, result.size());
		Assertions.assertEquals("In Progress", result.get(0).getStatusName());
		Assertions.assertEquals(LocalDate.of(2023, 10, 1), result.get(0).getDate());
		Assertions.assertEquals("Completed", result.get(1).getStatusName());
		Assertions.assertEquals(LocalDate.of(2023, 10, 15), result.get(1).getDate());

		Mockito.verify(startUpStatusRepository).findAllByStartUpId(startUpId);
		Mockito.verify(startUpMapper, Mockito.times(2)).entityToStartUpStatusDTO(Mockito.any(StartUpStatus.class));
	}

	@Test
	void getAllStartUpProgressStatuses_ReturnsEmptyList_WhenStartUpHasNoStatuses() {
		Long startUpId = 1L;

		Mockito.when(startUpStatusRepository.findAllByStartUpId(startUpId)).thenReturn(Collections.emptyList());

		List<GetStartUpStatusDTO> result = startUpService.getAllStartUpProgressStatuses(startUpId);

		Assertions.assertTrue(result.isEmpty());

		Mockito.verify(startUpStatusRepository).findAllByStartUpId(startUpId);
		Mockito.verify(startUpMapper, Mockito.never()).entityToStartUpStatusDTO(Mockito.any(StartUpStatus.class));
	}

	@Test
	void getAllStartUpProgressStatuses_ThrowsException_WhenStartUpStatusesNotFound() {
		Long startUpId = 1L;

		Mockito.when(startUpStatusRepository.findAllByStartUpId(startUpId)).thenReturn(Collections.emptyList());

		List<GetStartUpStatusDTO> result = startUpService.getAllStartUpProgressStatuses(startUpId);

		Assertions.assertTrue(result.isEmpty());
		Mockito.verify(startUpStatusRepository).findAllByStartUpId(startUpId);
	}

}
