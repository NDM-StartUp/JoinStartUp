package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.LoginHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class LoginHistoryRepositoryTest {

	@Autowired
	private LoginHistoryRepository loginHistoryRepository;

	@Test
	void testFindLoginHistoriesByUserId_ReturnsLoginHistories_WhenExist(){
		List<LoginHistory> loginHistories = loginHistoryRepository.findLoginHistoriesByUserId(1L);

		assertNotNull(loginHistories);
		assertFalse(loginHistories.isEmpty());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		assertEquals("2023-09-01 08:00:00", loginHistories.get(0).getDate().format(formatter));
	}

	@Test
	void testFindLoginHistoriesByUserId_ReturnsLoginHistories_WhenNotExist(){
		List<LoginHistory> loginHistories = loginHistoryRepository.findLoginHistoriesByUserId(999L);

		assertNotNull(loginHistories);
		assertTrue(loginHistories.isEmpty());
	}

}
