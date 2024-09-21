package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Exceptions.StatusAlreadyExistsConflictException;
import ndmstartup.joinstartup.Exceptions.UserChangeTypeConflictException;
import ndmstartup.joinstartup.Mappers.SupportTicketMapper;
import ndmstartup.joinstartup.Mappers.UserMapper;
import ndmstartup.joinstartup.Models.*;
import ndmstartup.joinstartup.Repositories.*;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployerRepository employerRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final StatusRepository statusRepository;
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
                () -> new NoSuchElementException("User not found with id " + userId)
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
    public void updateUserData(Long userId, PostUserDTO postUserDTO) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found with id " + userId)
        );

        existingUser.setFirstName(postUserDTO.getFirstName());
        existingUser.setLastName(postUserDTO.getLastName());
        existingUser.setEmail(postUserDTO.getEmail());
        existingUser.setPhone(postUserDTO.getPhone());
        existingUser.setDescription(postUserDTO.getDescription());

        userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found with id " + userId)
        );

        userRepository.delete(existingUser);
    }

    @Override
    public GetUserTypesDTO getUserTypes(Long userId) {
        GetUserTypesDTO userTypes = new GetUserTypesDTO();

        userTypes.setEmployee(employeeRepository.existsEmployeeById(userId));
        userTypes.setEmployer(employerRepository.existsEmployerById(userId));

        return userTypes;
    }

    @Override
    public GetUserInfoDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found with id " + userId)
        );

		return userMapper.EntityToInfoDTO(user);
    }

    @Override
    public List<GetLoginHistoryDTO> getLoginHistoryByUserId(Long userId) {
        List<LoginHistory> loginHistory = loginHistoryRepository.findLoginHistoriesByUserId(userId);

        return loginHistory.stream().map(
                rec -> GetLoginHistoryDTO.builder()
                        .loginDate(rec.getDate())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public void addStatusForUserById(Long userId, Long statusId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found with id " + userId)
        );
        Status status = statusRepository.findById(statusId).orElseThrow(
                () -> new NoSuchElementException("Status not found with id" + statusId)
        );

        if (!user.getStatuses().contains(status)) {
            user.getStatuses().add(status);
            userRepository.save(user);
        } else {
            throw new StatusAlreadyExistsConflictException(
                    "There is already status with id - " + statusId
                            + ", assigned to user with id - " + userId
            );
        }
    }

    @Override
    public List<GetUserStatusesDTO> getUserStatusesById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User not found with id " + userId)
        );
        return user.getStatuses().stream()
                .map(s -> new GetUserStatusesDTO(s.getName()))
                .collect(Collectors.toList());
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
