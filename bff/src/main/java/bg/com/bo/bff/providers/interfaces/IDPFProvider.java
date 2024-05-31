package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.DPFMWResponse;

import java.io.IOException;
import java.util.Map;

public interface IDPFProvider {
    DPFMWResponse getDPFsList(String personId, String deviceId, Map<String, String> parameters) throws IOException;
}
