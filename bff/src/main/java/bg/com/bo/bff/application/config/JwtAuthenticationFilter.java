package bg.com.bo.bff.application.config;

import bg.com.bo.bff.application.config.encryption.payload.EncryptionPayloadFilter;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.models.payload.encryption.RequestCompare;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtAccess;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.mappings.services.auth.IAuthMapper;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
@Order(2)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtProvider jwtService;
    private final IAuthMapper authMapper;

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

            chain.doFilter(request, response);
        } catch (GenericException e) {
            throw (e);
        } catch (Exception e) {
            logger.error("Hubo un error al obtener los datos del Access Token.");
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.AUTHENTICATION_FILTER_FAILURE);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        for (RequestCompare requestCompare : JwtAuthenticationFilter.getNotFilter()) {
            if (request.getMethod().equals(requestCompare.getMethod().name()) && path.matches(requestCompare.getPath()))
                return true;
        }
        return false;
    }

    @Getter
    private static final List<RequestCompare> notFilter;

    static {
        notFilter = List.of(
                new RequestCompare(HttpMethod.POST, "/api/v.*/login/[0-9]+/refresh")
        );
    }
}