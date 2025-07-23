package bg.com.bo.bff.mappings.providers.dpf;

import bg.com.bo.bff.application.dtos.response.dpf.DpfListResponse;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponse;

public interface IDPFMapper {
    DpfListResponse mapToDPFListResponse(DpfMWResponse mwResponse);
}
