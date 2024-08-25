package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.EmployeeCv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeCvRepository extends JpaRepository<EmployeeCv, Long> {

    List<EmployeeCv> findByEmployeeId(Long id);

}
