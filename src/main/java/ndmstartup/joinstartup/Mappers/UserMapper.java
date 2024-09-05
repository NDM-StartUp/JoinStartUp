package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.GetUserInfoDTO;
import ndmstartup.joinstartup.DTOs.PostUserDTO;
import ndmstartup.joinstartup.Models.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserMapper {

	public User DTOToEntity (PostUserDTO postUserDTO) {
		return User.builder()
				.firstName(postUserDTO.getFirstName())
				.lastName(postUserDTO.getLastName())
				.email(postUserDTO.getEmail())
				.phone(postUserDTO.getPhone())
				.description(postUserDTO.getDescription())
				.build();
	}

	public GetUserInfoDTO EntityToInfoDTO (User user) {
		return GetUserInfoDTO.builder()
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.description(user.getDescription())
				.build();
	}

}
