package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.StartUpEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartUpEmployeeRepository extends JpaRepository<StartUpEmployee, Long> {
    StartUpEmployee findByStartUpIdAndEmployeeId(Long startUpId, Long employeeId);
    StartUpEmployee findByEmployeeId(Long employeeId);
}
