package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;

import java.util.List;

public interface SupportTicketService {

    GetSupportTicketDTO getSupportTicketByTicketId(Long ticketId);

    GetSupportTicketDTO addSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO);

    List<GetSupportTicketDTO> getAllSupportTicketsByUserId(Long userId);

    List<GetSupportTicketDTO> getAllSupportTicketsByStatus(String status);

    List<GetSupportTicketDTO> getAllSupportTickets();

    void deleteSupportTicket(Long ticketId);

    GetSupportTicketDTO changeSupportTicketStatus(Long ticketId, String status);
}
