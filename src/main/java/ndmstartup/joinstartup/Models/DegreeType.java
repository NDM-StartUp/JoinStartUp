package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Degree_Type")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DegreeType {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50)
    private String name;

    @OneToMany(mappedBy = "degreeType", cascade = CascadeType.ALL)
    private List<EmployeeEducation> employeeEducations = new ArrayList<>();
}
