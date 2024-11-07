package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ApplicationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ApplicationStatusRepositoryTest {

	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;

	@Test
	public void testFindByName_returnsApplicationStatus_WhenExists() {
		Optional<ApplicationStatus> result = applicationStatusRepository.findByName("Accepted");

		assertTrue(result.isPresent());
		assertEquals("Accepted", result.get().getName());
	}

	@Test
	public void testFindByName_returnsOptionalEmpty_WhenNotExists() {
		Optional<ApplicationStatus> result = applicationStatusRepository.findByName("Published");

		assertFalse(result.isPresent());
	}
}
