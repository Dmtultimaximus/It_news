
package ITNews.project.ITNews.security;

import ITNews.project.ITNews.model.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtProvider {
    @Value("${secret.key}")
    private String secretKey;

    private static final String USER_ID = "userId";

    public String generateToken(UserEntity userEntity) {
        return Jwts.builder()
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .claim(USER_ID, userEntity.getUserId())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }
}
