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

		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2022, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2023, 4, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByStartupIdAndStatusAndDatePeriod(
				"Pending",
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
		assertEquals("Pending", byStartupIdAndStatus.get(0).getStatus().getName());

	}

	@Test
	void findByStartupIdAndStatusAndDatePeriod_NotFound() {

		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2022, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2021, 5, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByStartupIdAndStatusAndDatePeriod(
				"Pendingg",
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
	void findByEmployeeIdAndStatusAndDatePeriod() {

		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2022, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2023, 4, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByEmployeeIdAndStatusAndDatePeriod(
				"Pending",
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
		assertEquals("Pending", byStartupIdAndStatus.get(0).getStatus().getName());

	}

	@Test
	void findByEmployeeIdAndStatusAndDatePeriod_NotFound() {

		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2022, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2021, 4, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByEmployeeIdAndStatusAndDatePeriod(
				"Pendingg",
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
	void findByEmployeeIdAndStatus() {

		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByEmployeeIdAndStatus(
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
	void findByEmployeeIdAndStatus_NotFound() {

		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByEmployeeIdAndStatus(
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
	void findByEmployeeIdAndDatePeriod() {

		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2022, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2023, 4, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByEmployeeIdAndDatePeriod(
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
	void findByEmployeeIdAndDatePeriod_NotFound() {

		LocalDateTime startDate = LocalDateTime.of(LocalDate.of(2021, 4, 12), LocalTime.MAX);
		LocalDateTime endDate = LocalDateTime.of(LocalDate.of(2021, 5, 12), LocalTime.MAX);
		List<ApplicationCv> byStartupIdAndStatus = applicationRepository.findByEmployeeIdAndDatePeriod(
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
}