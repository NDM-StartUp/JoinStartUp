package ndmstartup.joinstartup.Security.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.Models.StartUp;
import ndmstartup.joinstartup.Repositories.StartUpRepository;
import ndmstartup.joinstartup.Security.Services.Interfaces.StartUpSecurityService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StartUpSecurityServiceImpl implements StartUpSecurityService {

    private final StartUpRepository startUpRepository;

    @Override
    public boolean isOwnerOfStartUp(Long startUpId, Long userId) {
        StartUp startUp = startUpRepository
                .findById(startUpId)
                .orElseThrow(()->new NoSuchElementException("Start up with id: " + startUpId + " not found"));
        return startUp.getEmployers().stream().anyMatch(employer -> employer.getId().equals(userId));
    }

    @Override
    public boolean isEmployeeOfStartUp(Long startUpId, Long userId) {
        StartUp startUp = startUpRepository
                .findById(startUpId)
                .orElseThrow(()->new NoSuchElementException("Start up with id: " + startUpId + " not found"));
        return startUp.getStartUpTeam().stream().anyMatch(employee -> employee.getId().equals(userId));
    }
}
