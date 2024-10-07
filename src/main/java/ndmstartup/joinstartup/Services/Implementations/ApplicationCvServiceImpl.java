package ndmstartup.joinstartup.Services.Implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Models.ApplicationCv;
import ndmstartup.joinstartup.Models.ApplicationStatus;
import ndmstartup.joinstartup.Repositories.ApplicationRepository;
import ndmstartup.joinstartup.Repositories.ApplicationStatusRepository;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationCvService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApplicationCvServiceImpl implements ApplicationCvService{
	private final ApplicationRepository applicationRepository;
	private final ApplicationStatusRepository applicationStatusRepository;

	@Transactional
	@Override
	public void updateApplicationStatus(Long applicationCvId, String applicationStatusName, Long startUpId) {
		ApplicationCv applicationCv = applicationRepository.findById(applicationCvId)
				.orElseThrow(() -> new NoSuchElementException("ApplicationCv not found with id: " + applicationCvId));

		ApplicationStatus applicationStatus = applicationStatusRepository.findByName(applicationStatusName)
				.orElseThrow(() -> new NoSuchElementException("ApplicationStatus not found with name: " + applicationStatusName));

		if(Objects.equals(applicationCv.getStartUp().getId(), startUpId)){
			throw new NoSuchElementException("Application with id: " + applicationCvId + " does not belong to StartUp with id: " + startUpId);
		}
		applicationCv.setStatus(applicationStatus);

		applicationRepository.save(applicationCv);
	}
}
