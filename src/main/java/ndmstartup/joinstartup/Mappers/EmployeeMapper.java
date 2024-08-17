package ndmstartup.joinstartup.Mappers;

import ndmstartup.joinstartup.DTOs.GetEducationDTO;
import ndmstartup.joinstartup.DTOs.GetExperienceDTO;
import ndmstartup.joinstartup.Models.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {

    public GetExperienceDTO entityToExperienceDTO(Employee employee) {

        return GetExperienceDTO.builder()
                .employeeId(employee.getId())
                .experience(employee.getEmployeePositions().stream().map((experience->GetExperienceDTO.ExperienceDTO.builder()
                        .workId(experience.getId())
                        .companyName(experience.getCompanyName())
                        .position(experience.getPosition())
                        .startingDate(experience.getStartingDate())
                        .endingDate(experience.getEndingDate())
                        .build())).toList())
                        .build();


    }

    public GetEducationDTO entityToEducationDTO(Employee employee) {

        return GetEducationDTO.builder()
                .employeeId(employee.getId())
                .educationList(employee.getEmployeeEducations().stream().map((education -> GetEducationDTO.EducationDTO.builder()
                        .educationId(education.getId())
                        .major(education.getMajor().getName())
                        .degreeType(education.getDegreeType().getName())
                        .beginningDate(education.getBeginningDate())
                        .finishingDate(education.getFinishingDate())
                        .university(GetEducationDTO.UniversityDTO.builder()
                                .name(education.getUniversity().getName())
                                .id(education.getUniversity().getId())
                                .Location(education.getUniversity().getLocation())
                                .build()).build())).toList())
                .build();

    }

}
