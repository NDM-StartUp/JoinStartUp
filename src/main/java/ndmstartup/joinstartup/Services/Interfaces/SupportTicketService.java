package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;

import java.util.List;

public interface SupportTicketService {

    GetSupportTicketDTO GetSupportTicketByTicketId(Long ticketId);

    GetSupportTicketDTO AddSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO);

    List<GetSupportTicketDTO> GetAllSupportTicketsByUserId(Long userId);

    List<GetSupportTicketDTO> GetAllSupportTicketsByStatus(String status);

    List<GetSupportTicketDTO> GetAllSupportTickets();

    void DeleteSupportTicket(Long ticketId);

    GetSupportTicketDTO ChangeSupportTicketStatus(Long ticketId, String status);
}
