
package ITNews.project.demo.security;

import ITNews.project.demo.model.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtProvider {
    @Value("${secret.key}")
    private String secretKey;
    private final Map<String, Object> email = new HashMap<String, Object>();
    public String generateToken(UserEntity userEntity) {
        email.put("email", userEntity.getEmail());
        return Jwts.builder()
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .claim("userId", userEntity.getUserId())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }
}
