package ndmstartup.joinstartup.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ndmstartup.joinstartup.Models.ApplicationStatus;
import ndmstartup.joinstartup.Models.Employee;

import java.time.LocalDate;
//    For application there should be cv. Should be implemented in the future
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class GetApplicationDTO {
    private Long id;
    private LocalDate applicationDate;
    private EmployeeCvDTO employeeCv;
    private ApplicationStatusDTO applicationStatus;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class EmployeeCvDTO {
        private String cvLink;
        private EmployeeDTO employee;
        @Data
        @Builder
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @AllArgsConstructor
        public static class EmployeeDTO {
            private Long employeeId;
        }
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    public static class ApplicationStatusDTO {
        private String applicationStatusName;
    }
}
