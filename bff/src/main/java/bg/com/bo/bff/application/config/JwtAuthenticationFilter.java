package bg.com.bo.bff.application.config;

import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.models.jwt.JwtAccess;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.mappings.interfaces.IAuthMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtProvider jwtService;
    private IAuthMapper authMapper;

    @Autowired
    public JwtAuthenticationFilter(IJwtProvider jwtService, IAuthMapper authMapper) {
        this.jwtService = jwtService;
        this.authMapper = authMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.split(" ")[1].trim();

            JwtAccess accessJwt = this.jwtService.parseJwtAccess(token);

            UserData userData = this.authMapper.convert(accessJwt);

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : accessJwt.getPayload().getRoles())
                authorities.add(new SimpleGrantedAuthority(role));

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userData, "", authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException ignored) {
        } catch (Exception e) {
            logger.error("Hubo un error al obtener los datos del Access Token.");
            logger.error(e);
        } finally {
            chain.doFilter(request, response);
        }
    }
}