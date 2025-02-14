package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
public class AppUserDetailsRepositoryTest {

	@Autowired
	private AppUserDetailsRepository appUserDetailsRepository;

	@Test
	public void testFindByEmail_ReturnsAppUserDetails_WhenEmailExists() {
		String existingEmail = "john.doe@example.com";

		Optional<AppUserDetails> appUserDetails = appUserDetailsRepository.findByEmail(existingEmail);

		assertTrue(appUserDetails.isPresent());
		assertEquals(existingEmail, appUserDetails.get().getEmail());
		assertEquals(1L, appUserDetails.get().getId());
	}
	@Test
	public void testFindByEmail_ReturnsOptionalEmpty_WhenEmailDoesNotExist() {
		String nonExistentEmail = "nonexistent@example.com";

		Optional<AppUserDetails> appUserDetails = appUserDetailsRepository.findByEmail(nonExistentEmail);

		assertFalse(appUserDetails.isPresent());
	}


}
