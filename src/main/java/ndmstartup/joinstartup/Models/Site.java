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
@Table(name = "Site")
@Getter
@Setter
@NoArgsConstructor
public class Site {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 25)
    private String name;

    @Column(name = "Domain_Name", nullable = false)
    @NotBlank(message = "Domain name is mandatory")
    @Size(max = 50)
    private String domainName;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private Set<Link> links = new HashSet<>();

}
