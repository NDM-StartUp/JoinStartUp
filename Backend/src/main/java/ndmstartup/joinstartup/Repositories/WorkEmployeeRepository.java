package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.WorkEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkEmployeeRepository extends JpaRepository<WorkEmployee, Long> {
}
