package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.dpf.DpfDataResponse;
import bg.com.bo.bff.application.dtos.response.dpf.DpfListResponse;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponse;
import bg.com.bo.bff.providers.interfaces.IDPFProvider;
import bg.com.bo.bff.mappings.providers.dpf.IDPFMapper;
import bg.com.bo.bff.services.interfaces.IDPFService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DPFService implements IDPFService {
    private final IDPFProvider dpfProvider;
    private final IDPFMapper idpfMapper;

    public DPFService(IDPFProvider dpfProvider, IDPFMapper idpfMapper) {
        this.dpfProvider = dpfProvider;
        this.idpfMapper = idpfMapper;
    }

    @Override
    public DpfListResponse getDPFsList(String personId, String deviceId, Map<String, String> parameters) throws IOException {
        DpfMWResponse mWResponse = dpfProvider.getDPFsList(
                personId,
                deviceId,
                parameters
        );

        DpfListResponse dpfListResponse = idpfMapper.mapToDPFListResponse(mWResponse);
        dpfListResponse.getData().sort(Comparator.comparing(DpfDataResponse::getExpirationDate));

        return dpfListResponse;
    }
}
