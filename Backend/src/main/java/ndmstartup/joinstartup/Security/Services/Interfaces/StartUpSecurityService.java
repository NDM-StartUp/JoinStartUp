package ndmstartup.joinstartup.Security.Services.Interfaces;

public interface StartUpSecurityService {
    boolean isOwnerOfStartUp(Long startUpId, Long userId);
    boolean isEmployeeOfStartUp(Long startUpId, Long userId);
}
