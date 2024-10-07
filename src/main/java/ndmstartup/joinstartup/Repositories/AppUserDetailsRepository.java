package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.AppUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDetailsRepository extends JpaRepository<AppUserDetails, Long> {
    Optional<AppUserDetails> findByEmail(String email);
}
