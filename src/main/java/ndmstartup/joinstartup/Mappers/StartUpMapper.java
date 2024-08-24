package ndmstartup.joinstartup.Mappers;

import lombok.RequiredArgsConstructor;
//import ndmstartup.joinstartup.DTOs.GetStartUpCompanyNameEmployeeDTO;
import ndmstartup.joinstartup.DTOs.*;
import ndmstartup.joinstartup.Models.StartUp;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StartUpMapper {
	public GetStartUpDTO entityToDTO(StartUp startUp){
		return GetStartUpDTO.builder()
				.id(startUp.getId())
				.name(startUp.getName())
				.companyName(startUp.getCompanyName())
				.description(startUp.getDescription())
				.requirements(startUp.getRequirements())
				.location(startUp.getLocation())
				.isPaid(startUp.isPaid())
				.build();
	}


	public GetStartUpIdEmployeeDTO entityToStartUpIdEmployeeDTO(StartUp startUp){
		return GetStartUpIdEmployeeDTO.builder()
				.startUpId(startUp.getId())
				.employeeDTOList(startUp.getStartUpTeam().stream().map(employee -> GetStartUpIdEmployeeDTO.EmployeeDTO.builder()
						.employeeId(employee.getId())
						.user(GetStartUpIdEmployeeDTO.UserDTO.builder()
								.firstName(employee.getEmployee().getUser().getFirstName())
								.lastName(employee.getEmployee().getUser().getLastName())
								.email(employee.getEmployee().getUser().getEmail())
								.phone(employee.getEmployee().getUser().getPhone())
								.build()).build()).toList())
				.build();
	}

	public GetStartUpCompanyNameEmployeeDTO entityToStartUpCompanyNameEmployeeDTO(StartUp startUp){
		return GetStartUpCompanyNameEmployeeDTO.builder()
				.startUpId(startUp.getId())
				.companyName(startUp.getCompanyName())
				.employeeDTOList(startUp.getStartUpTeam().stream().map(employee -> GetStartUpCompanyNameEmployeeDTO.EmployeeDTO.builder()
						.employeeId(employee.getId())
						.user(GetStartUpCompanyNameEmployeeDTO.UserDTO.builder()
								.firstName(employee.getEmployee().getUser().getFirstName())
								.lastName(employee.getEmployee().getUser().getLastName())
								.email(employee.getEmployee().getUser().getEmail())
								.phone(employee.getEmployee().getUser().getPhone())
								.build()).build()).toList())
				.build();
	}

	public GetStartUpLocationEmployeeDTO entityToStartUpLocationEmployeeDTO(StartUp startUp){
		return GetStartUpLocationEmployeeDTO.builder()
				.startUpId(startUp.getId())
				.location(startUp.getLocation())
				.employeeDTOList(startUp.getStartUpTeam().stream().map(employee -> GetStartUpLocationEmployeeDTO.EmployeeDTO.builder()
						.employeeId(employee.getId())
						.user(GetStartUpLocationEmployeeDTO.UserDTO.builder()
								.firstName(employee.getEmployee().getUser().getFirstName())
								.lastName(employee.getEmployee().getUser().getLastName())
								.email(employee.getEmployee().getUser().getEmail())
								.phone(employee.getEmployee().getUser().getPhone())
								.build()).build()).toList())
				.build();
	}

	public GetStartUpIsPaidEmployeeDTO entityToStartUpIsPaidEmployeeDTO(StartUp startUp){
		return GetStartUpIsPaidEmployeeDTO.builder()
				.startUpId(startUp.getId())
				.isPaid(startUp.isPaid())
				.employeeDTOList(startUp.getStartUpTeam().stream().map(employee -> GetStartUpIsPaidEmployeeDTO.EmployeeDTO.builder()
						.employeeId(employee.getId())
						.user(GetStartUpIsPaidEmployeeDTO.UserDTO.builder()
								.firstName(employee.getEmployee().getUser().getFirstName())
								.lastName(employee.getEmployee().getUser().getLastName())
								.email(employee.getEmployee().getUser().getEmail())
								.phone(employee.getEmployee().getUser().getPhone())
								.build()).build()).toList())
				.build();
	}
}
