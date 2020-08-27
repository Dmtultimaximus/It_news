package ITNews.project.ITNews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsRequest {
    private Long newsId;
    private Long userId;
    private String newsname;
    private String description;
    private String tags;
    private String text;
    private String urlImg;
    private double rating;
}
