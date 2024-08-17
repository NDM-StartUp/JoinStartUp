package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class GetStartUpDTO {

    private Long id;
    private String name;
    private String companyName;
    private String description;
    private String requirements;
    private String Location;
    private boolean isPaid;
    private List<String> startUpStatus;
    private List<String> skills;

}
