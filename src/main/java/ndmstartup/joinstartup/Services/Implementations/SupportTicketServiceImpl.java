package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.Mappers.SupportTicketMapper;
import ndmstartup.joinstartup.Models.SupportTicket;
import ndmstartup.joinstartup.Repositories.SupportTicketRepository;
import ndmstartup.joinstartup.Repositories.TicketStatusRepository;
import ndmstartup.joinstartup.Services.Interfaces.SupportTicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final TicketStatusRepository ticketStatusRepository;
    private final SupportTicketMapper supportTicketMapper;

    @Override
    public GetSupportTicketDTO getSupportTicketByTicketId(Long ticketId) {
        SupportTicket supportTicket = supportTicketRepository
                .findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Support ticket not found with id" + ticketId));

        return supportTicketMapper.entityToDTO(supportTicket);
    }

    @Override
    public List<GetSupportTicketDTO> getAllSupportTicketsByStatus(String status) {
        List<SupportTicket> supportTicket = supportTicketRepository
                .findAllByStatusName(status);

        return supportTicket.stream().map(supportTicketMapper::entityToDTO).toList();
    }

    @Override
    public List<GetSupportTicketDTO> getAllSupportTickets() {
        List<SupportTicket> supportTicket = supportTicketRepository.findAll();
        return supportTicket.stream().map(supportTicketMapper::entityToDTO).toList();
    }

    @Override
    public void deleteSupportTicket(Long ticketId) {

        SupportTicket supportTicket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Support ticket not found with id" + ticketId));

        supportTicketRepository.delete(supportTicket);
    }

    @Override
    public GetSupportTicketDTO changeSupportTicketStatus(Long ticketId, String status) {
        SupportTicket supportTicket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Support ticket not found with id" + ticketId));

        supportTicket.setTicketStatus(ticketStatusRepository.findByName(status)
                .orElseThrow(() -> new NoSuchElementException("Status name not found with name" + status)));
        supportTicket = supportTicketRepository.save(supportTicket);

        return supportTicketMapper.entityToDTO(supportTicket);
    }

}
