package ndmstartup.joinstartup.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserAuthInfoDTO {
    Long id;
    String email;
    Set<String> roles;
}
