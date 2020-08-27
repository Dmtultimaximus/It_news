package ITNews.project.ITNews.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.FetchType.LAZY;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "news_entity")
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long newsId;
    @NotBlank(message = "Post name can not be null")
    private String newsname;
    @NotEmpty
    private String description;
    private String tags;
    @NotEmpty
    private String text;
    @Nullable
    private String urlImg;
    @Nullable
    private Float rating;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserEntity user;
    private LocalDateTime createdDate;

}

