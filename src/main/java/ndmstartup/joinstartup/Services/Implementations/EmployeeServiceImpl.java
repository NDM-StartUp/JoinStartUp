package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Mappers.EducationMapper;
import ndmstartup.joinstartup.Mappers.EmployeeMapper;
import ndmstartup.joinstartup.Models.Employee;
import ndmstartup.joinstartup.Models.EmployeeEducation;
import ndmstartup.joinstartup.Models.Skill;
import ndmstartup.joinstartup.Models.WorkEmployee;
import ndmstartup.joinstartup.Repositories.EmployeeEducationRepository;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Repositories.SkillRepository;
import ndmstartup.joinstartup.Repositories.WorkEmployeeRepository;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final SkillRepository skillRepository;
    private final WorkEmployeeRepository workEmployeeRepository;
    private final EmployeeEducationRepository employeeEducationRepository;
    private final EducationMapper educationMapper;


    @Override
    public GetEmployeeExperienceDTO getExperienceByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found with id" + employeeId));

        return employeeMapper.entityToExperienceDTO(employee);
    }

    @Override
    public GetEmployeeEducationDTO getEducationByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found with id" + employeeId));

        return employeeMapper.entityToEducationDTO(employee);
    }

    @Override
    public GetEmployeeSkillsDTO getSkillsByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found with id" + employeeId));

        return employeeMapper.entityToSkillsDTO(employee);
    }

    @Override
    public void deleteEmployeeSkill(Long employeeId, Long skillId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found with id" + employeeId));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new NoSuchElementException("Skill not found with id" + skillId));
        if (employee.getSkills().remove(skill))
            employeeRepository.save(employee);
        else
            throw new NoSuchElementException("Employee with id: " + employeeId + " does not have skill with id: " + skillId);
    }

    @Override
    @Transactional
    public void addSkillToEmployee(Long employeeId, Long skillId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found with id" + employeeId));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new NoSuchElementException("Skill not found with id" + skillId));
        employee.getSkills().add(skill);
        employeeRepository.save(employee);
    }

    @Override
    public void addExperienceByEmployeeId(Long employeeId, PostEmployeeExperienceDTO experience) {
        WorkEmployee workEmployee = WorkEmployee.builder()
                .employee(employeeRepository.findById(employeeId).orElseThrow(()->new NoSuchElementException("Employee not found with id" + employeeId)))
                .companyName(experience.getCompanyName())
                .position(experience.getPosition())
                .startingDate(experience.getStartingDate())
                .endingDate(experience.getEndingDate())
                .build();

        workEmployeeRepository.save(workEmployee);
    }

    @Override
    public void addEducationByEmployeeId(Long employeeId, PostEmployeeEducationDTO education) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found with id" + employeeId));
        EmployeeEducation employeeEducation = educationMapper.postDtoToEntity(education, employee);
        employeeEducationRepository.save(employeeEducation);
    }


}
