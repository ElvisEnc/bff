package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@Order(3)
public class EncryptionPayloadFilter extends OncePerRequestFilter {

    @Value("${encryption.url.exclude.pattern}")
    private String urlExcludePatterns;

    @Value("${encryption.exclude.key}")
    private String encryptionExcludeKey;

    @Value("${payload.algorithm}")
    private String payloadAlgorithm;

    private IEncryptionService encryptionService;

    private static final Logger logger = LogManager.getLogger(EncryptionPayloadFilter.class.getName());

    @Autowired
    public EncryptionPayloadFilter(IEncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            DecryptRequestWrapper decryptRequestWrapper = new DecryptRequestWrapper(request, encryptionService, payloadAlgorithm);
            EncryptResponseWrapper responseWrapper = new EncryptResponseWrapper(response, encryptionService, decryptRequestWrapper.getEncodeInfo(), payloadAlgorithm);

            filterChain.doFilter(decryptRequestWrapper, responseWrapper);

            response.getWriter().write(responseWrapper.getContent());
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error to process the request/response.");
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String eek = request.getHeader(Constants.ENCRYPTION_EXCLUDE_KEY_HEADER);
        if (encryptionExcludeKey != null && eek != null
                && !eek.isBlank() && !encryptionExcludeKey.isBlank()
                && eek.equals(encryptionExcludeKey))
            return true;

        String path = request.getServletPath();
        String[] urlPatterns = urlExcludePatterns.split(",");

        for (String reg : urlPatterns)
            if (path.matches(reg))
                return true;

        return false;
    }
}
