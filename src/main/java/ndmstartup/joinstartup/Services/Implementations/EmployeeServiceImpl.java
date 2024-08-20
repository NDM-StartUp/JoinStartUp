package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetEmployeeApplicationsDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeSkillsDTO;
import ndmstartup.joinstartup.Mappers.EmployeeMapper;
import ndmstartup.joinstartup.Models.Employee;
import ndmstartup.joinstartup.Models.Skill;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Repositories.SkillRepository;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    private SkillRepository skillRepository;

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
        return null;
    }


}
