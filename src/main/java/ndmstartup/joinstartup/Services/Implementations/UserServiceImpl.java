package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostUserDTO;
import ndmstartup.joinstartup.Exceptions.UserChangeTypeConflictException;
import ndmstartup.joinstartup.Mappers.SupportTicketMapper;
import ndmstartup.joinstartup.Mappers.UserMapper;
import ndmstartup.joinstartup.Models.Employee;
import ndmstartup.joinstartup.Models.Employer;
import ndmstartup.joinstartup.Models.SupportTicket;
import ndmstartup.joinstartup.Models.User;
import ndmstartup.joinstartup.Repositories.EmployeeRepository;
import ndmstartup.joinstartup.Repositories.EmployerRepository;
import ndmstartup.joinstartup.Repositories.SupportTicketRepository;
import ndmstartup.joinstartup.Repositories.UserRepository;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final UserMapper userMapper;
    private final SupportTicketRepository supportTicketRepository;
    private final SupportTicketMapper supportTicketMapper;

    @Override
    @Transactional
    public void addUser(PostUserDTO postUserDTO, boolean isEmployee) {
        User user = userMapper.DTOToEntity(postUserDTO);
        user.setRegistrationDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        userRepository.save(user);

        if (isEmployee) {
            Employee employee = new Employee();
            employee.setUser(user);
            employeeRepository.save(employee);
        } else {
            Employer employer = new Employer();
            employer.setUser(user);
            employerRepository.save(employer);
        }
    }

    @Override
    @Transactional
    public void addUserRole (Long userId, boolean addEmployerRole) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("There is no user with id - " + userId)
        );

        if (addEmployerRole) {
            if (employerRepository.existsEmployerById(userId)) {
                throw new UserChangeTypeConflictException("User of id - " + userId + ", is already an Employer.");
            }
            Employer employer = new Employer();
            employer.setUser(user);
            employerRepository.save(employer);
        } else {
            if (employeeRepository.existsEmployeeById(userId)) {
                throw new UserChangeTypeConflictException("User of id - " + userId + ", is already an Employee.");
            }
            Employee employee = new Employee();
            employee.setUser(user);
            employeeRepository.save(employee);
        }
    }

    @Override
    public List<GetSupportTicketDTO> getAllSupportTicketsByUserId(Long userId) {
        List<SupportTicket> supportTicket = supportTicketRepository
                .findAllByUserId(userId);

        return supportTicket.stream().map(supportTicketMapper::entityToDTO).toList();
    }

    @Override
    @Transactional
    public GetSupportTicketDTO addSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO) {
        SupportTicket supportTicket = supportTicketMapper.DTOToEntity(supportTicketDTO, userId);

        supportTicket = supportTicketRepository.save(supportTicket);

        return supportTicketMapper.entityToDTO(supportTicket);
    }

}
