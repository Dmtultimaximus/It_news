package ITNews.project.ITNews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsControllerResponse {
    private Long newsId;
    private String type;
    private String message;
    private Boolean success;
}
