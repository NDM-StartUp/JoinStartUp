package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.Services.Interfaces.SupportTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support-ticket")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    @GetMapping("/{ticketId}")
    public ResponseEntity<GetSupportTicketDTO> getSupportTicketByTicketId(@PathVariable Long ticketId) {
        GetSupportTicketDTO supportTicket = supportTicketService.getSupportTicketByTicketId(ticketId);
        return ResponseEntity.ok(supportTicket);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<List<GetSupportTicketDTO>> getSupportTicketByStatus(@PathVariable String status) {
        List<GetSupportTicketDTO> supportTicket = supportTicketService.getAllSupportTicketsByStatus(status);
        return ResponseEntity.ok(supportTicket);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetSupportTicketDTO>> getAllSupportTickets() {
        List<GetSupportTicketDTO> supportTicket = supportTicketService.getAllSupportTickets();
        return ResponseEntity.ok(supportTicket);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteSupportTicket(@PathVariable Long ticketId) {
        supportTicketService.deleteSupportTicket(ticketId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{ticketId}/status")
    public ResponseEntity<GetSupportTicketDTO> changeSupportTicketStatus(@PathVariable Long ticketId,
                                                                         @RequestParam("status") String status) {
        GetSupportTicketDTO supportTicket = supportTicketService.changeSupportTicketStatus(ticketId, status);
        return ResponseEntity.ok(supportTicket);
    }

}

