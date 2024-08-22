package ndmstartup.joinstartup.Services.Interfaces;

import ndmstartup.joinstartup.DTOs.*;

import java.util.List;

public interface UserService {

    void addUser (PostUserDTO postUserDTO, boolean isEmployee);

    void addUserRole (Long userId, boolean addEmployerRole);

    void updateUserData (Long userId, PostUserDTO postUserDTO);

    void deleteUser (Long userId);

    GetUserTypesDTO getUserTypes (Long userId);
    GetUserInfoDTO getUserInfo (Long userId);

    List<GetLoginHistoryDTO> getLoginHistoryByUserId (Long userId);

    GetSupportTicketDTO addSupportTicket(Long userId, PostSupportTicketDTO supportTicketDTO);

    List<GetSupportTicketDTO> getAllSupportTicketsByUserId(Long userId);

}
