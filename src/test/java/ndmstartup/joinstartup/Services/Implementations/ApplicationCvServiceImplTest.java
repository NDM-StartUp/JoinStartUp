package ndmstartup.joinstartup.Services.Implementations;

import ndmstartup.joinstartup.Models.ApplicationCv;
import ndmstartup.joinstartup.Models.ApplicationStatus;
import ndmstartup.joinstartup.Models.StartUp;
import ndmstartup.joinstartup.Repositories.ApplicationRepository;
import ndmstartup.joinstartup.Repositories.ApplicationStatusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ApplicationCvServiceImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private  ApplicationStatusRepository applicationStatusRepository;

    @InjectMocks
    private ApplicationCvServiceImpl applicationCvService;

    @Mock
    private ApplicationCv applicationCv;

    @Mock
    private ApplicationStatus applicationStatus;

    @Mock
    private StartUp startUp;


    @Test
    void updateApplicationStatus_Success() {

        Long applicationCvId = 1L;
        String applicationStatusName = "APPLIED";
        Long startUpId = 1L;

        Mockito.when(applicationRepository.findById(applicationCvId)).thenReturn(Optional.of(applicationCv));
        Mockito.when(applicationStatusRepository.findByName(applicationStatusName)).thenReturn(Optional.of(applicationStatus));
        Mockito.when(applicationCv.getStartUp()).thenReturn(startUp);
        Mockito.when(startUp.getId()).thenReturn(startUpId);

        applicationCvService.updateApplicationStatus(applicationCvId, applicationStatusName, startUpId);

        Mockito.verify(applicationCv).setStatus(applicationStatus);
        Mockito.verify(applicationRepository).save(applicationCv);

    }

    @Test
    void updateApplicationStatus_ThrowsNoSuchElementException_WhenApplicationCvNotFound() {

        Long applicationCvId = 1L;
        String applicationStatusName = "APPLIED";
        Long startUpId = 1L;

        Mockito.when(applicationRepository.findById(applicationCvId)).thenReturn(Optional.empty());


        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            applicationCvService.updateApplicationStatus(applicationCvId, applicationStatusName, startUpId);
        });
        Assertions.assertEquals("ApplicationCv not found with id: " + applicationCvId, exception.getMessage());

    }

    @Test
    void updateApplicationStatus_ThrowsNoSuchElementException_WhenApplicationStatusNotFound() {

        Long applicationCvId = 1L;
        String applicationStatusName = "APPLIED";
        Long startUpId = 1L;

        Mockito.when(applicationRepository.findById(applicationCvId)).thenReturn(Optional.of(applicationCv));
        Mockito.when(applicationStatusRepository.findByName(applicationStatusName)).thenReturn(Optional.empty());


        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            applicationCvService.updateApplicationStatus(applicationCvId, applicationStatusName, startUpId);
        });
        Assertions.assertEquals("ApplicationStatus not found with name: " + applicationStatusName, exception.getMessage());

    }

    @Test
    void updateApplicationStatus_ThrowsNoSuchElementException_WhenApplicationDoesNotBelongToStartUp() {

        Long applicationCvId = 1L;
        String applicationStatusName = "APPLIED";
        Long startUpId = 1L;

        Mockito.when(applicationRepository.findById(applicationCvId)).thenReturn(Optional.of(applicationCv));
        Mockito.when(applicationStatusRepository.findByName(applicationStatusName)).thenReturn(Optional.of(applicationStatus));
        Mockito.when(applicationCv.getStartUp()).thenReturn(startUp);
        Mockito.when(startUp.getId()).thenReturn(startUpId);


        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            applicationCvService.updateApplicationStatus(applicationCvId, applicationStatusName, startUpId+1L);
        });
        Assertions.assertEquals("Application with id: " + applicationCvId + " does not belong to StartUp with id: " + (startUpId+1L), exception.getMessage());

    }
}