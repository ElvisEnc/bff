package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.registry.RegistryRequest;
import bg.com.bo.bff.application.dtos.response.RegistryResponse;

import java.security.NoSuchAlgorithmException;

public interface IRegistryService {
    RegistryResponse registerByMigration(RegistryRequest registryRequest) throws NoSuchAlgorithmException;
}
