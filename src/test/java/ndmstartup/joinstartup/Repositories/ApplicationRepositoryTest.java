package ndmstartup.joinstartup.Repositories;

import jakarta.transaction.Transactional;
import ndmstartup.joinstartup.Models.ApplicationCv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Test
    void findByStartupIdAndStatus() {
        List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByStartupIdAndStatus(
                "Pending",
                1L
        );

        assertTrue(byStartupIdAndStatus.contains(applicationRepository.findById(1L).get()));
        assertEquals("Pending", byStartupIdAndStatus.get(0).getStatus().getName());
    }

    @Test
    void findByStartupIdAndDatePeriod() {
    }

    @Test
    void findByStartupIdAndStatusAndDatePeriod() {
    }

    @Test
    void findByEmployeeIdAndStatusAndDatePeriod() {
    }

    @Test
    void findByEmployeeIdAndStatus() {
    }

    @Test
    void findByEmployeeIdAndDatePeriod() {
    }
}