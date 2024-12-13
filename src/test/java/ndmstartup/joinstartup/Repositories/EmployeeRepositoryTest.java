package ndmstartup.joinstartup.Repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class EmployeeRepositoryTest {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void testExistsEmployeeById_returnTrue_WhenExist(){
		boolean exists = employeeRepository.existsEmployeeById(1L);

		assertTrue(exists);
	}

	@Test
	public void testExistsEmployeeById_ReturnsFalse_WhenEmployeeDoesNotExist() {

		boolean exists = employeeRepository.existsEmployeeById(3L);

		assertFalse(exists);
	}
}
