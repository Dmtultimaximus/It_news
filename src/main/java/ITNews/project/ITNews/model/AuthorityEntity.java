package ITNews.project.ITNews.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authority")
public class AuthorityEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long roleId;

    @Column(name = "role")
    private String authority;
}
