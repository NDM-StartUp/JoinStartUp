package ndmstartup.joinstartup.Repositories;

import ndmstartup.joinstartup.Models.AppUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserDetailsRepository extends JpaRepository<AppUserDetails, Long> {
    Optional<AppUserDetails> findByEmail(String email);
}
