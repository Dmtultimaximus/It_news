package ITNews.project.ITNews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Long userId;
    private String authenticationToken;
    private String username;
    private Boolean success;
}
