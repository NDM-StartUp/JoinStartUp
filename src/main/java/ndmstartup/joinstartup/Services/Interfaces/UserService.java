package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;

import java.util.List;

public interface UserService {

    GetSupportTicketDTO addSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO);

    List<GetSupportTicketDTO> getAllSupportTicketsByUserId(Long userId);

}
