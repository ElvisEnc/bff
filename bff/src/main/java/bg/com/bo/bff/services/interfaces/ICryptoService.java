package bg.com.bo.bff.services.interfaces;

import java.io.IOException;
import java.util.Map;


public interface ICryptoService {

    void validateCrypto(String description, Map<String, String> parameter) throws IOException;
}






