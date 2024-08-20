package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetEmployeeEducationDTO;
import ndmstartup.joinstartup.DTOs.GetEmployeeExperienceDTO;
import ndmstartup.joinstartup.Mappers.EmployeeMapper;
import ndmstartup.joinstartup.Models.Employee;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Services.Interfaces.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    @Override
    public GetEmployeeExperienceDTO getExperienceByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new NoSuchElementException("Employee not found"));

        return employeeMapper.entityToExperienceDTO(employee);
    }

    @Override
    public GetEmployeeEducationDTO getEducationByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(()->new NoSuchElementException("Employee not found"));

        return employeeMapper.entityToEducationDTO(employee);
    }


}
