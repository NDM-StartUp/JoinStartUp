package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUpEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StartUpEmployeeRepository extends JpaRepository<StartUpEmployee, Long> {
    Optional<StartUpEmployee> findByStartUpIdAndEmployeeId(Long startUpId, Long employeeId);
    Optional<StartUpEmployee> findByEmployeeId(Long employeeId);
}
