package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;

import java.util.List;

public interface SupportTicketService {

    GetSupportTicketDTO getSupportTicketByTicketId(Long ticketId);

    List<GetSupportTicketDTO> getAllSupportTicketsByStatus(String status);

    List<GetSupportTicketDTO> getAllSupportTickets();

    void deleteSupportTicket(Long ticketId);

    GetSupportTicketDTO changeSupportTicketStatus(Long ticketId, String status);
}
