
package br.ufma.sppg.Security;
 

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.ufma.sppg.model.Usuario;


@Service
public class JwtService  {
    
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create().withIssuer("auth-api")
            .withSubject(user.getEmail())
            .withExpiresAt(genExpirationDate())
            .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar o Token",e);
            
        }
    }
    
    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        
    }

    public String isValidToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
            .withIssuer("auth-api")
            .build()
            .verify(token)
            .getSubject();

        } catch (JWTVerificationException e) {
            
            return " " ;
        }
    }


   
}