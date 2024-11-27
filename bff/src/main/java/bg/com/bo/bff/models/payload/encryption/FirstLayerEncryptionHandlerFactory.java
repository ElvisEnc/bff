package bg.com.bo.bff.models.payload.encryption;

import bg.com.bo.bff.application.config.encryption.payload.EncryptionPayloadFilter;
import bg.com.bo.bff.models.payload.encryption.FirstLayerEncryptionAnonymousUserHandler;
import bg.com.bo.bff.models.payload.encryption.FirstLayerEncryptionKnowUserHandler;
import bg.com.bo.bff.models.payload.encryption.IFirstLayerEncryptionHandler;
import bg.com.bo.bff.models.payload.encryption.RequestCompare;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.http.HttpServletRequest;

public class FirstLayerEncryptionHandlerFactory {
    private FirstLayerEncryptionHandlerFactory() {
    }

    public static IFirstLayerEncryptionHandler instance(HttpServletRequest request, IEncryptionService encryptionService) {
        for (RequestCompare requestCompare : EncryptionPayloadFilter.getAnonymousEncryptionUrls()) {
            if (request.getMethod().equals(requestCompare.getMethod().name()) && request.getServletPath().matches(requestCompare.getPath()))
                return new FirstLayerEncryptionAnonymousUserHandler(request, encryptionService);
        }
        return new FirstLayerEncryptionKnowUserHandler(request, encryptionService);
    }
}