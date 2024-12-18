package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.enums.EnvProfile;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionHeaders;
import bg.com.bo.bff.models.payload.encryption.FirstLayerEncryptionHandlerFactory;
import bg.com.bo.bff.models.payload.encryption.IFirstLayerEncryptionHandler;
import bg.com.bo.bff.models.payload.encryption.RequestCompare;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class EncryptionPayloadFilter extends OncePerRequestFilter {

    @Value("${encryption.url.exclude.pattern}")
    private String urlExcludePatterns;

    @Value("${encryption.exclude.key}")
    private String encryptionExcludeKey;

    @Value("${payload.algorithm}")
    private String payloadAlgorithm;

    private final IEncryptionService encryptionService;
    private final Environment env;

    private static final Logger logger = LogManager.getLogger(EncryptionPayloadFilter.class.getName());

    @Autowired
    public EncryptionPayloadFilter(IEncryptionService encryptionService, Environment env) {
        this.encryptionService = encryptionService;
        this.env = env;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            request.setAttribute("encrypted", true);
            IFirstLayerEncryptionHandler firstLayerEncryptionHandler = FirstLayerEncryptionHandlerFactory.instance(request, encryptionService);
            DecryptRequestWrapper decryptRequestWrapper = new DecryptRequestWrapper(request, payloadAlgorithm, firstLayerEncryptionHandler);
            EncryptResponseWrapper responseWrapper = new EncryptResponseWrapper(response, firstLayerEncryptionHandler, payloadAlgorithm);

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
        String eek = request.getHeader(EncryptionHeaders.ENCRYPTION_EXCLUDED_KEY_HEADER.getCode());
        if (!Arrays.stream(env.getActiveProfiles()).toList().contains(EnvProfile.prod.name())
                && (encryptionExcludeKey != null && eek != null
                    && !eek.isBlank() && !encryptionExcludeKey.isBlank()
                    && eek.equals(encryptionExcludeKey)))
            return true;

        String path = request.getServletPath();
        String[] urlPatterns = urlExcludePatterns.split(",");

        for (String reg : urlPatterns)
            if (path.matches(reg))
                return true;

        for (RequestCompare requestCompare : EncryptionPayloadFilter.getExcludedEncryptionUrls()) {
            if (request.getMethod().equals(requestCompare.getMethod().name()) && path.matches(requestCompare.getPath()))
                return true;
        }

        return false;
    }

    @Getter
    private static final List<RequestCompare> anonymousEncryptionUrls;

    static {
        anonymousEncryptionUrls = List.of(
                new RequestCompare(HttpMethod.POST, "/api/v.*/registry/device/migration"),
                new RequestCompare(HttpMethod.GET, "/api/v.*/login/validate-device")
        );
    }

    @Getter
    private static final List<RequestCompare> excludedEncryptionUrls;

    static {
        excludedEncryptionUrls = List.of(
                new RequestCompare(HttpMethod.GET, "/api/v.*/registry/device/handshake"),
                new RequestCompare(HttpMethod.GET, "/api/v.*/users/contact"),
                new RequestCompare(HttpMethod.GET, "/api/v.*/attention-points/points/[0-9]+"),
                new RequestCompare(HttpMethod.GET, "/api/v.*/attention-points/points/[0-9]+/tickets")
        );
    }
}
