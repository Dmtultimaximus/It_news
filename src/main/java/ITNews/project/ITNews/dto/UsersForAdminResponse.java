package ITNews.project.ITNews.dto;

import ITNews.project.ITNews.model.AuthorityEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersForAdminResponse {
    private Long userId;
    private String username;
    private String email;
    private Boolean enabled;
    private List<AuthorityEntity> authority;
}
