package bg.com.bo.bff.providers.mappings.dpf;

import bg.com.bo.bff.application.dtos.response.DPFListResponse;
import bg.com.bo.bff.providers.dtos.responses.DPFMWResponse;

public interface IDPFMapper {
    DPFListResponse mapToDPFListResponse(DPFMWResponse mwResponse);
}
