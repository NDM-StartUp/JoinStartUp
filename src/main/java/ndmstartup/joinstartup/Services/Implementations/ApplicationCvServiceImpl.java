package ndmstartup.joinstartup.Services.Implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Models.ApplicationCv;
import ndmstartup.joinstartup.Models.ApplicationStatus;
import ndmstartup.joinstartup.Repositories.ApplicationCvRepository;
import ndmstartup.joinstartup.Repositories.ApplicationStatusRepository;
import ndmstartup.joinstartup.Services.Interfaces.ApplicationCvService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ApplicationCvServiceImpl implements ApplicationCvService{
	private final ApplicationCvRepository applicationCvRepository;
	private final ApplicationStatusRepository applicationStatusRepository;

	@Transactional
	@Override
	public void updateApplicationStatus(Long applicationCvId, String applicationStatusName) {
		ApplicationCv applicationCv = applicationCvRepository.findById(applicationCvId)
				.orElseThrow(() -> new NoSuchElementException("ApplicationCv not found with id: " + applicationCvId));

		ApplicationStatus applicationStatus = applicationStatusRepository.findByName(applicationStatusName)
				.orElseThrow(() -> new NoSuchElementException("ApplicationStatus not found with name: " + applicationStatusName));

		applicationCv.setStatus(applicationStatus);

		applicationCvRepository.save(applicationCv);
	}
}
