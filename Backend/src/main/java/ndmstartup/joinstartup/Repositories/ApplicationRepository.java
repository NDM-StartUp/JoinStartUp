package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.ApplicationCv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationCv, Long> {

	@Query("SELECT a FROM ApplicationCv a JOIN a.startUp s JOIN a.status ss WHERE ss.name = :applicationStatus AND s.id = :startUpId")
	List<ApplicationCv> findByStartupIdAndStatus(@Param("applicationStatus") String applicationStatus, @Param("startUpId") Long startUpId);

	@Query("SELECT a FROM ApplicationCv a JOIN a.startUp s WHERE a.date BETWEEN :startDate AND :endDate AND s.id = :startUpId")
	List<ApplicationCv> findByStartupIdAndDatePeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("startUpId") Long startUpId);

	@Query("SELECT a FROM ApplicationCv a JOIN a.startUp s JOIN a.status ss WHERE ss.name = :applicationStatus AND s.id = :startUpId AND a.date BETWEEN :startDate AND :endDate")
	List<ApplicationCv> findByStartupIdAndStatusAndDatePeriod(@Param("applicationStatus") String applicationStatus, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("startUpId") Long startUpId);

	@Query("SELECT a FROM ApplicationCv a JOIN a.employeeCv ec JOIN a.status ss WHERE ss.name = :applicationStatus AND ec.employee.id = :employeeId AND a.date BETWEEN :startDate AND :endDate")
	List<ApplicationCv> findByEmployeeIdAndStatusAndDatePeriod(@Param("applicationStatus") String applicationStatus, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("employeeId") Long employeeId);

	@Query("SELECT a FROM ApplicationCv a JOIN a.employeeCv ec JOIN a.status ss WHERE ss.name = :applicationStatus AND ec.employee.id = :employeeId")
	List<ApplicationCv> findByEmployeeIdAndStatus(@Param("applicationStatus") String applicationStatus, @Param("employeeId") Long employeeId);

	@Query("SELECT a FROM ApplicationCv a JOIN a.employeeCv ec WHERE a.date BETWEEN :startDate AND :endDate AND ec.employee.id = :employeeId")
	List<ApplicationCv> findByEmployeeIdAndDatePeriod(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("employeeId") Long employeeId);
}

