package br.ufma.sppg.Security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.ufma.sppg.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private UsuarioService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
    
        String servletPath = request.getServletPath();
        if (servletPath.contains("api/authentication")  || servletPath.contains("docs")){
            filterChain.doFilter(request, response);
            return;
        }
        
        
        if (request.getMethod().equalsIgnoreCase("POST") && request.getRequestURI().endsWith("/api/usuario")){
            filterChain.doFilter(request, response);
            return;
        }      
         
        
    
        String token = request.getHeader("Authorization");
        String userId = request.getHeader("RequestedBy");
        
        
        if (token == null || userId == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("User nao autenticado");
            return;
        }
        
        boolean isValidToken = jwtService.isValidToken(token.replace("Bearer ", ""), UUID.fromString(userId));
        
        if (!isValidToken){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
           
            response.getWriter().write("Token inválido ou expirado");
            return;
        }
        if (isValidToken){ 
            try {
                var user = userService.getUserById(UUID.fromString(userId));

                var authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.getWriter().write(e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }else {
            response.getWriter().write("Token inválido!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
       
        filterChain.doFilter(request, response);
    }
}