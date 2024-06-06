package bg.com.bo.bff.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IdentityValidationInterceptor())
                .excludePathPatterns(
                        "/api/v*/login/{personId:[0-9]+}/refresh",
                        "/api/v*/users/{personId:[0-9]+}/biometric");
    }
}