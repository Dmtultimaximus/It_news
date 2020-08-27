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
public class UserRaitingNewsEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long ratingId;
    private Long userId;
    private Long newsId;
    private Byte rating;
}
