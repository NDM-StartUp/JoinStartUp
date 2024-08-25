package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetEmployeeApplicationsDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeSkillsDTO;
import ndmstartup.joinstartup.Mappers.ApplicationMapper;
import ndmstartup.joinstartup.Mappers.EmployeeMapper;
import ndmstartup.joinstartup.Models.ApplicationCv;
import ndmstartup.joinstartup.Models.Employee;
import ndmstartup.joinstartup.Models.Skill;
import ndmstartup.joinstartup.Repositories.ApplicationRepository;
import ndmstartup.joinstartup.Repositories.EmployeeCvRepository;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Repositories.SkillRepository;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final ApplicationMapper applicationMapper;
    private final SkillRepository skillRepository;
    private final EmployeeCvRepository employeeCvRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public GetEmployeeExperienceDTO getExperienceByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));

        return employeeMapper.entityToExperienceDTO(employee);
    }

    @Override
    public GetEmployeeEducationDTO getEducationByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));

        return employeeMapper.entityToEducationDTO(employee);
    }

    @Override
    public GetEmployeeSkillsDTO getSkillsByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));

        return employeeMapper.entityToSkillsDTO(employee);
    }

    @Override
    public void deleteEmployeeSkill(Long employeeId, Long skillId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new NoSuchElementException("Skill not found"));
        if (employee.getSkills().remove(skill))
            employeeRepository.save(employee);
        else
            throw new NoSuchElementException("Employee with id: " + employeeId + " does not have skill with id: " + skillId);
    }

    @Override
    @Transactional
    public void addSkillToEmployee(Long employeeId, Long skillId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new NoSuchElementException("Skill not found"));
        employee.getSkills().add(skill);
        employeeRepository.save(employee);
    }

    @Override
    public GetEmployeeApplicationsDTO getApplicationsByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee not found"));
        List<ApplicationCv> applications = employeeCvRepository.findByEmployeeId(employeeId).stream()
                .flatMap(employeeCv -> employeeCv.getCvs().stream())
                .toList();

        return GetEmployeeApplicationsDTO.builder()
                .employeeId(employeeId)
                .applications(applications.stream()
                        .map(applicationMapper::entityToDTO)
                        .toList())
                .build();
    }

    @Override
    public void deleteApplicationByApplicationId(Long applicationId) {
        applicationRepository.findById(applicationId).orElseThrow(() -> new NoSuchElementException("Application not found"));
        applicationRepository.deleteById(applicationId);
    }


}
