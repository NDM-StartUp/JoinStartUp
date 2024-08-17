package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.Models.Status;
import ndmstartup.joinstartup.Models.SupportTicket;
import ndmstartup.joinstartup.Repositories.TicketStatusRepository;
import ndmstartup.joinstartup.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class SupportTicketMapper {

    private final UserRepository userRepository;
    private final TicketStatusRepository ticketStatusRepository;

    public GetSupportTicketDTO entityToDTO(SupportTicket supportTicket) {
        return GetSupportTicketDTO.builder()
                .id(supportTicket.getId())
                .name(supportTicket.getName())
                .description(supportTicket.getDescription())
                .user(
                        GetSupportTicketDTO.UserDTO.builder()
                                .id(supportTicket.getUser().getId())
                                .firstName(supportTicket.getUser().getFirstName())
                                .lastName(supportTicket.getUser().getLastName())
                                .email(supportTicket.getUser().getEmail())
                                .phone(supportTicket.getUser().getPhone())
                                .statuses(supportTicket.getUser().getStatuses()
                                        .stream().map(Status::getName).toList())
                                .build()
                        )
                .ticketStatus(supportTicket.getTicketStatus().getName())
                .build();
    }

    public SupportTicket DTOToEntity(PostSupportTicketDTO supportTicket, Long userId) {
        return SupportTicket.builder()
                .name(supportTicket.getName())
                .description(supportTicket.getDescription())
                .user(userRepository.getReferenceById(userId))
                .ticketStatus(ticketStatusRepository.findByName("Open")
                        .orElseThrow(()->new NoSuchElementException("Ticket status with the status name Open does not exist")))
                .build();

    }
}
