package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
import ndmstartup.joinstartup.DTOs.PostUserDTO;
import ndmstartup.joinstartup.Models.User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserMapper {

	public User DTOToEntity (PostUserDTO postUserDTO) {
		return User.builder()
				.id(postUserDTO.getId())
				.firstName(postUserDTO.getFirstName())
				.lastName(postUserDTO.getLastName())
				.email(postUserDTO.getEmail())
				.phone(postUserDTO.getPhone())
				.description(postUserDTO.getDescription())
				.build();
	}

}
