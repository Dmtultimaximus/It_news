package ITNews.project.ITNews.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorApi {
    private String message;
    private int codeError;
    private LocalDateTime timeError = LocalDateTime.now();
}
