package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Models.Employee;
import ndmstartup.joinstartup.Models.StartUpEmployee;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeMapper {

    private final SkillMapper skillMapper;
    private final PositionMapper positionMapper;
    private final StartUpMapper startUpMapper;
    private final EducationMapper educationMapper;

    public GetEmployeeExperienceDTO entityToExperienceDTO(Employee employee) {

        return GetEmployeeExperienceDTO.builder()
                .employeeId(employee.getId())
                .experience(employee.getEmployeePositions().stream().map((experience -> GetEmployeeExperienceDTO.ExperienceDTO.builder()
                        .workId(experience.getId())
                        .companyName(experience.getCompanyName())
                        .position(experience.getPosition())
                        .startingDate(experience.getStartingDate())
                        .endingDate(experience.getEndingDate())
                        .build())).toList())
                .build();


    }

    public GetEmployeeEducationDTO entityToEducationDTO(Employee employee) {

        return GetEmployeeEducationDTO.builder()
                .employeeId(employee.getId())
                .educationList(employee.getEmployeeEducations().stream()
                        .map((educationMapper::entityToDto))
                        .toList())
                .build();
    }

    public GetEmployeeSkillsDTO entityToSkillsDTO(Employee employee) {
        return GetEmployeeSkillsDTO.builder()
                .employeeId(employee.getId())
                .skills(employee.getSkills().stream().map(skillMapper::entityToDTO).toList())
                .build();
    }

    public GetEmployeePositionDTO entityToPositionDTO(StartUpEmployee startUpEmployee) {
        return GetEmployeePositionDTO.builder()
                .employeeId(startUpEmployee.getEmployee().getId())
                .positionInStartUp(
                        GetEmployeePositionDTO.GetPositionInStartUpDTO.builder()
                                .position(positionMapper.entityToDTO(startUpEmployee.getPosition()))
                                .startUp(startUpMapper.entityToDTO(startUpEmployee.getStartUp()))
                                .build()
                ).build();

    }
}
