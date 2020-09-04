package ITNews.project.ITNews.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long userId;
//    @Min(3)
    @NonNull
    private String username;
//    @Min(5)
    @NotNull
    private String password;
//    @Email
    private String email;
    private LocalDateTime created;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
                joinColumns = @JoinColumn(name = "user_id_fk"),
                inverseJoinColumns = @JoinColumn(name = "authority_id_fk"))
    private List<AuthorityEntity> authorities = new ArrayList<>();
    private boolean enabled;
    @Transient
    private boolean accountNonExpired = true;
    @Transient
    private boolean accountNonLocked = true;
    @Transient
    private boolean credentialsNonExpired = true;
}
