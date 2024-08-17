package ndmstartup.joinstartup.Services.Implementations;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetSupportTicketDTO;
import ndmstartup.joinstartup.DTOs.PostSupportTicketDTO;
import ndmstartup.joinstartup.Mappers.SupportTicketMapper;
import ndmstartup.joinstartup.Models.SupportTicket;
import ndmstartup.joinstartup.Repositories.SupportTicketRepository;
import ndmstartup.joinstartup.Services.Interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SupportTicketRepository supportTicketRepository;
    private final SupportTicketMapper supportTicketMapper;

    @Override
    public List<GetSupportTicketDTO> getAllSupportTicketsByUserId(Long userId) {
        List<SupportTicket> supportTicket = supportTicketRepository
                .findAllByUserId(userId);

        return supportTicket.stream().map(supportTicketMapper::entityToDTO).toList();
    }

    @Override
    @Transactional
    public GetSupportTicketDTO addSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO) {
        SupportTicket supportTicket = supportTicketMapper.DTOToEntity(supportTicketDTO, userId);

        supportTicket = supportTicketRepository.save(supportTicket);

        return supportTicketMapper.entityToDTO(supportTicket);
    }

}
