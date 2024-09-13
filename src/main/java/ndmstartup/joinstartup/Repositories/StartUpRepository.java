package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StartUpRepository extends JpaRepository<StartUp, Long> {

//	Optional<StartUp> findByCompanyName (String companyName);
//	Optional<StartUp> findByLocation (String location);
//	Optional<StartUp> findByIsPaid (boolean isPaid);

	@Query("SELECT s FROM StartUp s WHERE (:startUpId IS NULL OR s.id = :startUpId) " +
			"AND (:companyName IS NULL OR s.companyName = :companyName) " +
			"AND (:location IS NULL OR s.location = :location) " +
			"AND (:isPaid IS NULL OR s.isPaid = :isPaid)")
	List<StartUp> findByIdOrCompanyNameOrLocationOrIsPaid(@Param("startUpId") Long startUpId,
														  @Param("companyName") String companyName,
														  @Param("location") String location,
														  @Param("isPaid") Boolean isPaid);

	@Query("SELECT s FROM StartUp s WHERE (:companyName IS NULL OR s.companyName = :companyName) " +
			"AND (:location IS NULL OR s.location = :location) " +
			"AND (:isPaid IS NULL OR s.isPaid = :isPaid)")
	List<StartUp> findByCriteria(@Param("companyName") String companyName,
								 @Param("location") String location,
								 @Param("isPaid") Boolean isPaid);

	@Query("SELECT su FROM StartUp su " +
			"JOIN StartUpStatus sus ON su.id = sus.startUp.id " +
			"JOIN ProgressStatus ps ON sus.progressStatus.id = ps.id " +
			"WHERE ps.name = :progressStatusName")
	List<StartUp> findByProgressStatus(@Param("progressStatusName") String progressStatusName);
}


