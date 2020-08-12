package ITNews.project.ITNews.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token_auth")
public class TokenAuthEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;
    private Long userId;
    private String userToken;
    private LocalDateTime time_token;
    private boolean enabled;

}
