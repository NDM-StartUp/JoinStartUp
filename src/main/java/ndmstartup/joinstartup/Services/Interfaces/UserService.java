package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostUserDTO;

import java.util.List;

public interface UserService {

    void addUser (PostUserDTO postUserDTO, boolean isEmployee);

    void addUserRole (Long userId, boolean addEmployerRole);

    GetSupportTicketDTO addSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO);

    List<GetSupportTicketDTO> getAllSupportTicketsByUserId(Long userId);

}
