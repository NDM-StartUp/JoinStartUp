package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ApplicationCv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationCv, Long> {
	@Query("SELECT a FROM ApplicationCv a JOIN a.startUp s JOIN s.startUpStatus ss JOIN ss.progressStatus ps WHERE ps.name = :startupStatus")
	List<ApplicationCv> findByStartupStatus(@Param("startupStatus") String startupStatus);

	@Query("SELECT a FROM ApplicationCv a WHERE a.date BETWEEN :startDate AND :endDate")
	List<ApplicationCv> findByDatePeriod(@Param("startDate") Date  startDate, @Param("endDate") Date endDate);

	@Query("SELECT a FROM ApplicationCv a JOIN a.startUp s JOIN s.startUpStatus ss JOIN ss.progressStatus ps WHERE ps.name = :startupStatus AND a.date BETWEEN :startDate AND :endDate")
	List<ApplicationCv> findByStartupStatusAndDatePeriod(@Param("startupStatus") String startupStatus, @Param("startDate") Date  startDate, @Param("endDate") Date  endDate);
}
