package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetApplicationDTO;
import ndmstartup.joinstartup.Models.ApplicationCv;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationMapper {
	public GetApplicationDTO entityToDTO(ApplicationCv application) {
		return GetApplicationDTO.builder()
				.id(application.getId())
				.applicationDate(application.getDate())
				.employeeCv(GetApplicationDTO.EmployeeCvDTO.builder()
						.cvLink(application.getEmployeeCv().getLink())
						.employee(GetApplicationDTO.EmployeeCvDTO.EmployeeDTO.builder()
								.employeeId(application.getEmployeeCv().getEmployee().getUser().getId())
								.build())
						.build())
				.applicationStatus(GetApplicationDTO.ApplicationStatusDTO.builder()
						.applicationStatusName(application.getStatus().getName())
						.build())
				.build();
	}
}
