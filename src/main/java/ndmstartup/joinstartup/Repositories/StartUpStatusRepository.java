package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUpStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StartUpStatusRepository extends JpaRepository<StartUpStatus, Long> {

	@Query("SELECT sus FROM StartUpStatus sus WHERE sus.startUp.id = :startUpId")
	Optional<StartUpStatus> findByStartUpId(@Param("startUpId") Long startUpId);

	@Query("SELECT sus FROM StartUpStatus sus JOIN FETCH sus.progressStatus WHERE sus.startUp.id = :startUpId")
	List<StartUpStatus> findAllByStartUpId(@Param("startUpId") Long startUpId);
}
