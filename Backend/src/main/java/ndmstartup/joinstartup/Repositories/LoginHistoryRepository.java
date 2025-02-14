package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
	List<LoginHistory> findLoginHistoriesByUserId (Long userId);
}
