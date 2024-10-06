package ndmstartup.joinstartup.Services.Interfaces;

public interface ApplicationCvService {
	void updateApplicationStatus(Long applicationCvId, String applicationStatusName, Long startUpId);
}
