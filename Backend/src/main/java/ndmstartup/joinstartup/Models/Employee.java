package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Employee")
@Getter
@Setter
@NoArgsConstructor
public class Employee {
	@Id
	@Column(name = "User_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name = "User_Id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<EmployeeEducation> employeeEducations = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "Employee_Skill",
			joinColumns = @JoinColumn(name = "Skill_Id"),
			inverseJoinColumns = @JoinColumn(name = "Employee_Id")
	)
	private List<Skill> skills = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<EmployeeCv> employeeCvs = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<WorkEmployee> employeePositions = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<StartUpEmployee> startUpTeam = new ArrayList<>();
}
