package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class StartUpRepositoryTest {

	@Autowired
	private StartUpRepository startUpRepository;

	@Test
	public void testFindByIdOrCompanyNameOrLocationOrIsPaid() {
		List<StartUp> result = startUpRepository.findByIdOrCompanyNameOrLocationOrIsPaid(
				null,
				"TechCo",
				null,
				null
		);

		assertEquals(1, result.size());
		assertEquals("TechCo", result.get(0).getCompanyName());
	}

	@Test
	public void testFindByCriteria() {
		List<StartUp> result = startUpRepository.findByCriteria("BuildIT", "Boston", true);
		assertEquals(1, result.size());
		assertEquals("Boston", result.get(0).getLocation());
	}

	@Test
	public void testFindAllByUserId_User1_ReturnsOneStartUp() {
		List<StartUp> result = startUpRepository.findAllByUserId(1L);

		assertEquals(1, result.size());
		assertEquals("TechCo", result.get(0).getCompanyName());
	}

	@Test
	public void testFindAllByUserId_User999_ReturnsEmpty() {
		List<StartUp> result = startUpRepository.findAllByUserId(999L);

		assertEquals(0, result.size());
	}


	@Test
	public void testFindByProgressStatus() {
		List<StartUp> result = startUpRepository.findByProgressStatus("Failed");
		assertEquals(1, result.size());
		assertEquals("BuildIT", result.get(0).getCompanyName());
	}
}
