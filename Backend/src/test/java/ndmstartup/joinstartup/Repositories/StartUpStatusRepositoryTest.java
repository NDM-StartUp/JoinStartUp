package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class StartUpStatusRepositoryTest {

    @Autowired
    private StartUpStatusRepository startUpStatusRepository;

    @Test
    void findByStartUpId() {

        Optional<StartUpStatus> byStartUpId = startUpStatusRepository.findByStartUpId(1L);
        assertTrue(byStartUpId.isPresent());

    }

    @Test
    void findByStartUpId_NotFound() {

        Optional<StartUpStatus> byStartUpId = startUpStatusRepository.findByStartUpId(-2L);
        assertTrue(byStartUpId.isEmpty());

    }

    @Test
    void findAllByStartUpId() {

        List<StartUpStatus> allByStartUpId = startUpStatusRepository.findAllByStartUpId(1L);
        assertFalse(allByStartUpId.isEmpty());

    }

    @Test
    void findAllByStartUpId_NotFound() {

        List<StartUpStatus> allByStartUpId = startUpStatusRepository.findAllByStartUpId(-2L);
        assertTrue(allByStartUpId.isEmpty());

    }
}