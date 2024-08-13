package ndmstartup.joinstartup.Controllers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.Services.Implementations.SupportTicketServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/support-ticket")
public class SupportTicketController {

    private final SupportTicketServiceImpl supportTicketService;

    @GetMapping("/by-ticket-id")
    public ResponseEntity<GetSupportTicketDTO> GetSupportTicketByTicketId(@RequestParam("ticketId") Long ticketId) {

            GetSupportTicketDTO supportTicket = supportTicketService.GetSupportTicketByTicketId(ticketId);

            return ResponseEntity.ok(supportTicket);

    }

    @GetMapping("/by-user-id")
    public ResponseEntity<List<GetSupportTicketDTO>> GetSupportTicketByUserId(@RequestParam("userId") Long userId) {

        List<GetSupportTicketDTO> supportTicket = supportTicketService.GetAllSupportTicketsByUserId(userId);

        return ResponseEntity.ok(supportTicket);

    }

    @GetMapping("/by-status")
    public ResponseEntity<List<GetSupportTicketDTO>> GetSupportTicketByStatus(@RequestParam("status") String status) {

        List<GetSupportTicketDTO> supportTicket = supportTicketService.GetAllSupportTicketsByStatus(status);

        return ResponseEntity.ok(supportTicket);

    }

    @GetMapping("/all")
    public ResponseEntity<List<GetSupportTicketDTO>> GetAllSupportTickets() {

        List<GetSupportTicketDTO> supportTicket = supportTicketService.GetAllSupportTickets();

        return ResponseEntity.ok(supportTicket);

    }

    @PostMapping
    public ResponseEntity<GetSupportTicketDTO> AddSupportTicket(@RequestParam("userId") Long userId
            , @RequestBody PostSupportTicketDTO supportTicketDTO) {

        GetSupportTicketDTO supportTicket = supportTicketService.AddSupportTicket(userId, supportTicketDTO);

        return ResponseEntity.ok(supportTicket);

    }

    @DeleteMapping
    public ResponseEntity<Void> DeleteSupportTicket(@RequestParam("ticketId") Long ticketId) {

        supportTicketService.DeleteSupportTicket(ticketId);

        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<GetSupportTicketDTO> ChangeSupportTicketStatus(@RequestParam("ticketId") Long ticketId, @RequestParam("status") String status) {

        GetSupportTicketDTO supportTicket = supportTicketService.ChangeSupportTicketStatus(ticketId, status);

        return ResponseEntity.ok(supportTicket);

    }

}
