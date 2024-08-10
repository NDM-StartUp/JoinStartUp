package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "StartUp")
@Getter
@Setter
@NoArgsConstructor
public class StartUp {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50)
    private String name;

    @Column(name = "Company_Name")
    @NotBlank(message = "Company name is mandatory")
    @Size(max = 50)
    private String companyName;

    @Column(name = "Description", nullable = false)
    @NotBlank(message = "Description is mandatory")
    @Size(max = 500)
    private String description;

    @Column(name = "Requirements", nullable = false)
    @NotBlank(message = "Requirements is mandatory")
    @Size(max = 200)
    private String requirements;

    @Column(name = "Location", nullable = false)
    @NotBlank(message = "Location is mandatory")
    @Size(max = 100)
    private String Location;

    @Column(name = "Is_Paid", nullable = false)
    @NotBlank(message = "Is is paid or not is mandatory")
    private boolean isPaid;

    @OneToMany(mappedBy = "startUp", cascade = CascadeType.ALL)
    private Set<StartUpStatus> startUpStatus = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "Required_Skill",
            joinColumns = @JoinColumn(name = "StartUp_Id"),
            inverseJoinColumns = @JoinColumn(name = "Skill_Id")
    )
    private Set<Skill> skills = new HashSet<>();

}
