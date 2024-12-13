package ndmstartup.joinstartup.Repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class EmployerRepositoryTest {

	@Autowired
	private EmployerRepository employerRepository;

	@Test
	public void testExistsEmployerById_ReturnsTrue_WhenEmployerExists() {

		boolean exists = employerRepository.existsEmployerById(1L);

		assertTrue(exists);
	}

	@Test
	public void testExistsEmployerById_ReturnsFalse_WhenEmployerDoesNotExist() {

		boolean exists = employerRepository.existsEmployerById(3L);

		assertFalse(exists);
	}
}