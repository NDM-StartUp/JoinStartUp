package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Work_Employee")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkEmployee {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Employee_Id", nullable = false)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "Position_Id", nullable = false)
	private Position position;

	@Column(name = "Company_Name", nullable = false)
	@NotBlank(message = "Company name is mandatory.")
	@Size(max = 50)
	private String companyName;

	@Column(name = "Starting_Date", nullable = false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private LocalDate startingDate;

	@Column(name = "Ending_Date")
	@Temporal(TemporalType.DATE)
	private LocalDate endingDate;
}
