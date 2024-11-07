package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUpEmployee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class StartUpEmployeeRepositoryTest {

	@Autowired
	private StartUpEmployeeRepository startUpEmployeeRepository;

	@Test
	public void testFindByStartUpIdAndEmployeeId() {
		StartUpEmployee result = startUpEmployeeRepository.findByStartUpIdAndEmployeeId(1L, 1L);

		assertEquals("Doe", result.getEmployee().getUser().getLastName());
		assertEquals("TechCo", result.getStartUp().getCompanyName());
	}

	@Test
	public void testFindByEmployeeId() {
		StartUpEmployee result = startUpEmployeeRepository.findByEmployeeId(2L);

		assertEquals("Smith", result.getEmployee().getUser().getLastName());
	}
}
