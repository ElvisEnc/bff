package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.application.dtos.response.ErrorResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
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
        } catch (GenericException e) {
            sendResponse(response, e);
        } catch (Exception e) {
            logger.error("Error to process the request/response.");
            logger.error(e);
            sendResponse(response, DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendResponse(HttpServletResponse response, IMiddlewareError error) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(error.getCode())
                .message(error.getMessage())
                .build();
        response.setStatus(error.getHttpCode().value());
        response.setContentType("application/json");
        response.getWriter().write(Util.objectToString(errorResponse));
    }

    private static void sendResponse(HttpServletResponse response, GenericException e) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();
        response.setStatus(e.getStatus().value());
        response.setContentType("application/json");
        response.getWriter().write(Util.objectToString(errorResponse));
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
