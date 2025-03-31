package bg.com.bo.bff.application.config;

import bg.com.bo.bff.commons.enums.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Value("${spring.security.allowed.urls}")
    private String[] allowedUrls;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationFilter jwtTokenFilter) {
        this.jwtAuthenticationFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowedUrls).permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v*/registry/device/migration").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v*/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v*/login/{personId:[0-9]+}/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/login/validate-device").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/users/{personId:[0-9]+}/biometric").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/users/contact").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/attention-points/points").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/attention-points/points/{pointId:[0-9]+}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/attention-points/points/{pointId:[0-9]+}/tickets").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/registry/device/handshake").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v*/frequently-question/questions").permitAll()
                        .anyRequest().hasAuthority(UserRole.LOGGED_USER.toString())
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
