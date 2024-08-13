package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.Mappers.SupportTicketMapper;
import ndmstartup.joinstartup.Models.SupportTicket;
import ndmstartup.joinstartup.Repositories.SupportTicketRepository;
import ndmstartup.joinstartup.Repositories.TicketStatusRepository;
import ndmstartup.joinstartup.Services.Interfaces.SupportTicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final TicketStatusRepository ticketStatusRepository;
    private final SupportTicketMapper supportTicketMapper;

    @Override
    public GetSupportTicketDTO GetSupportTicketByTicketId(Long ticketId) {
        SupportTicket supportTicket = supportTicketRepository
                .findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Support ticket does not exist"));

        return supportTicketMapper.entityToGetDTO(supportTicket);
    }

    @Override
    @Transactional
    public GetSupportTicketDTO AddSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO) {
        SupportTicket supportTicket = supportTicketMapper.PostDTOToEntity(supportTicketDTO, userId);

        supportTicket = supportTicketRepository.save(supportTicket);

        return supportTicketMapper.entityToGetDTO(supportTicket);
    }

    @Override
    public List<GetSupportTicketDTO> GetAllSupportTicketsByUserId(Long userId) {
        List<SupportTicket> supportTicket = supportTicketRepository
                .findAllByUserId(userId);

        return supportTicket.stream().map(supportTicketMapper::entityToGetDTO).toList();
    }

    @Override
    public List<GetSupportTicketDTO> GetAllSupportTicketsByStatus(String status) {
        List<SupportTicket> supportTicket = supportTicketRepository
                .findAllByStatusName(status);

        return supportTicket.stream().map(supportTicketMapper::entityToGetDTO).toList();
    }

    @Override
    public List<GetSupportTicketDTO> GetAllSupportTickets() {
        List<SupportTicket> supportTicket = supportTicketRepository.findAll();
        return supportTicket.stream().map(supportTicketMapper::entityToGetDTO).toList();
    }

    @Override
    public void DeleteSupportTicket(Long ticketId) {

        SupportTicket supportTicket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Support ticket does not exist"));

        supportTicketRepository.delete(supportTicket);
    }

    @Override
    public GetSupportTicketDTO ChangeSupportTicketStatus(Long ticketId, String status) {
        SupportTicket supportTicket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new NoSuchElementException("Support ticket does not exist"));

        supportTicket.setTicketStatus(ticketStatusRepository.findByName(status)
                .orElseThrow(() -> new NoSuchElementException("Status name does not exist")));

        supportTicket = supportTicketRepository.save(supportTicket);

        return supportTicketMapper.entityToGetDTO(supportTicket);
    }

}
