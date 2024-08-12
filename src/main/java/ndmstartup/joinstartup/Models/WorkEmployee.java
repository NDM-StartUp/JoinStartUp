package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Work_Employee")
@Getter
@Setter
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
	private Date startingDate;

	@Column(name = "Ending_Date")
	@Temporal(TemporalType.DATE)
	private Date endingDate;
}