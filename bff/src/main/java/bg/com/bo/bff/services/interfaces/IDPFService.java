package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.DPFListResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IDPFService {
    DPFListResponse getDPFsList(String personId, String deviceId, Map<String, String> parameter) throws Exception;
}
