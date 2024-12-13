package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Employee_Cv")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeCv {
	@Id
	@Column(name = "EmployeeCv_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "Employee_Id", nullable = false)
	private Employee employee;

	@Column(name = "Link", nullable = false)
	@NotBlank(message = "Link to a CV is mandatory.")
	@Size(max = 100)
//	@Pattern(regexp = "^(https?://)?([a-zA-Z0-9.-]+)(:[0-9]{1,5})?(/[a-zA-Z0-9()@:%_+.~#?&/=-]*)?$\n", message = "Invalid link provided.")
	private String link;

	@OneToMany(mappedBy = "employeeCv", cascade = CascadeType.ALL)
	private List<ApplicationCv> cvs = new ArrayList<>();
}
