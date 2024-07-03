package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.dpf.DpfListResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IDPFService {
    DpfListResponse getDPFsList(String personId, String deviceId, Map<String, String> parameter) throws Exception;
}
