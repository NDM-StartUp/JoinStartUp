package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ApplicationCv;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
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
		if (applicationRepository.findById(1L).isEmpty()) {
			fail("ApplicationCV is not in repository. " +
					"Most likely data.sql file for tests is configured incorrectly.");
		}
		assertTrue(byStartupIdAndStatus.contains(applicationRepository.findById(1L).get()));
		assertEquals("Pending", byStartupIdAndStatus.get(0).getStatus().getName());
	}

	@Test
	void findByStartupIdAndStatus_ReturnsEmptyWhenNotFound() {
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByStartupIdAndStatus(
				"NotExistingStatus",
				1L
		);
		if (applicationRepository.findById(1L).isEmpty()) {
			fail("ApplicationCV is not in repository. " +
					"Most likely data.sql file for tests is configured incorrectly.");
		}
		assertTrue(byStartupIdAndStatus.isEmpty());
	}

	@Test
	void findByStartupIdAndDatePeriod() {

		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2022, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2023, 4, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByStartupIdAndDatePeriod(
				startDate,
				endDate,
				1L
		);
		if (applicationRepository.findById(1L).isEmpty()) {
			fail("ApplicationCV is not in repository. " +
					"Most likely data.sql file for tests is configured incorrectly.");
		}
		assertTrue(byStartupIdAndStatus.contains(applicationRepository.findById(1L).get()));
		assertTrue(byStartupIdAndStatus.get(0).getDate().isAfter(startDate)
				&& byStartupIdAndStatus.get(0).getDate().isBefore(endDate));
	}

	@Test
	void findByStartupIdAndDatePeriod_ReturnsEmptyWhenNotFound() {
		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2021, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2021, 5, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByStartupIdAndDatePeriod(
				startDate,
				endDate,
				1L
		);
		if (applicationRepository.findById(1L).isEmpty()) {
			fail("ApplicationCV is not in repository. " +
					"Most likely data.sql file for tests is configured incorrectly.");
		}
		assertTrue(byStartupIdAndStatus.isEmpty());
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