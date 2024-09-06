package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeePositionDTO {

    private Long employeeId;
    private GetPositionInStartUpDTO positionInStartUp;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetPositionInStartUpDTO{
        private GetPositionDTO position;
        private GetStartUpDTO startUp;
    }
}
