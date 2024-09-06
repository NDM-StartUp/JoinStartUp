package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.EmployeeEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeEducationRepository extends JpaRepository<EmployeeEducation, Long> {
}
