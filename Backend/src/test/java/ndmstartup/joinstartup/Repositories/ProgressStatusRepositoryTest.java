package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ProgressStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProgressStatusRepositoryTest {

	@Autowired
	private ProgressStatusRepository progressStatusRepository;

	@Test
	public void testFindByName_returnsProgressStatus_whenExists() {
		Optional<ProgressStatus> result = progressStatusRepository.findByName("In Progress");

		assertTrue(result.isPresent());
		assertEquals("In Progress", result.get().getName());
	}

	@Test
	public void testFindByName_returnsOptionalEmpty_whenNotExists() {
		Optional<ProgressStatus> result = progressStatusRepository.findByName("Delayed");

		assertFalse(result.isPresent());
	}
}
