package ITNews.project.ITNews.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ImgNewsEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long idImg;
    private Long idNews;
    private String nameImg;
    private String urlImg;
    private String cloudIdImg;
    private boolean mainImg;
}
