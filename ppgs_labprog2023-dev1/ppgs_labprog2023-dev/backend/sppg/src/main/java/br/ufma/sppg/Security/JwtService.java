
package br.ufma.sppg.Security;
 
import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements IJwtService {
    
    private final long EXPIRATION_TIME  = 7200000;
    private final String KEY  = "50655368566D597133743677397A24432646294A404E635266546A576E5A7234\n";

    @Override
    public String generateToken(UUID userId) {
        return Jwts
        .builder()
        .setSubject(userId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(genSignInKey(), SignatureAlgorithm.HS256)
        .compact();
    }
    @Override
    public boolean isValidToken(String token, UUID userId) {
        try {
            // Faz o parsing do token, verificando a assinatura e decodificando os dados do token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(genSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Recupera o ID de usuário e a data de expiração do token
            String subject = claims.getSubject();
            Date expiration = claims.getExpiration();

            // Verifica se o ID de usuário corresponde ao fornecido e se o token ainda é válido
            return userId.toString().equals(subject) && expiration != null && expiration.after(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            // Trata a exceção se o token for inválido ou expirado
            return false;
        }
    }


    private Key genSignInKey(){
        return Keys.hmacShaKeyFor( Decoders.BASE64.decode(KEY));
    }
}