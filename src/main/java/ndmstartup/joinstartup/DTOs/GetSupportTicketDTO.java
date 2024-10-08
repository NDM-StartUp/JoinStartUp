package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSupportTicketDTO {

    private Long id;
    private String name;
    private String description;
    private UserDTO user;
    private String ticketStatus;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO
    {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private List<String> statuses = new ArrayList<>();
    }
}

