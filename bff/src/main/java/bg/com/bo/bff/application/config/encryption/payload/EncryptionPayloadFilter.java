package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.exceptions.NotEncodedInfoException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
@Order(1)
public class EncryptionPayloadFilter extends OncePerRequestFilter {

    @Value("${encryption.url.exclude.pattern}")
    private String urlExcludePatterns;

    @Value("${encryption.exclude.key}")
    private String encryptionExcludeKey;

    private IEncryptionService encryptionService;

    private static final Logger logger = LogManager.getLogger(EncryptionPayloadFilter.class.getName());

    @Autowired
    public EncryptionPayloadFilter(IEncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            DecryptRequestWrapper decryptRequestWrapper = new DecryptRequestWrapper(request, encryptionService);
            EncryptResponseWrapper responseWrapper = new EncryptResponseWrapper(response, encryptionService, decryptRequestWrapper.getEncodeInfo());

            filterChain.doFilter(decryptRequestWrapper, responseWrapper);

            response.getWriter().write(responseWrapper.getContent());
        } catch (NotEncodedInfoException e) {
            logger.error("Encryption parameters not valid.");

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .build();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(Util.objectToString(errorResponse));
        } catch (Exception e) {
            logger.error("Error to process the request/response.");
            logger.error(e);

            ErrorResponse errorResponse = ErrorResponse.builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                    .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .build();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(Util.objectToString(errorResponse));
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
