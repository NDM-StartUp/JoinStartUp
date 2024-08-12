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
@Table(name = "Skill")
@Getter
@Setter
@NoArgsConstructor
public class Skill {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50)
    private String name;

    @ManyToMany(mappedBy = "skills")
    private List<StartUp> startUps = new ArrayList<>();

    @ManyToMany(mappedBy = "skills")
    private List<Employee> employees = new ArrayList<>();
}
