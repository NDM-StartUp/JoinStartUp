package ndmstartup.joinstartup.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Link")
@Getter
@Setter
@NoArgsConstructor
public class Link {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Link", nullable = false)
    @NotBlank(message = "Link is mandatory")
    @Size(max = 50)
    private String link;

    @ManyToOne
    @JoinColumn(name="Site_Id", nullable=false)
    private Site site;

    @ManyToOne
    @JoinColumn(name = "User_Id", nullable = false)
    private User user;
}
