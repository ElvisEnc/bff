package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenValidationEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import bg.com.bo.bff.mappings.providers.softtoken.ISoftTokenMapper;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenMWRequest;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenValidationEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;
import bg.com.bo.bff.providers.interfaces.ISoftTokenProvider;
import bg.com.bo.bff.services.interfaces.ISoftTokenService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SoftTokenService implements ISoftTokenService {
    private final ISoftTokenProvider provider;
    private final ISoftTokenMapper mapper;

    public SoftTokenService(ISoftTokenProvider idcProvider, ISoftTokenMapper idcMapper) {
        this.provider = idcProvider;
        this.mapper = idcMapper;
    }

    @Override
    public SoftTokenWelcomeResponse getWelcomeMessage(String personId ) throws IOException {
        SoftTokenMWRequest mwRequest = mapper.mapperRequest(personId);
        SoftTokenWelcomeMWResponse mwResponse = provider.getWelcomeMessage(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public SoftTokenDataEnrollmentResponse getDataEnrollment(String personId) throws IOException {
        SoftTokenMWRequest mwRequest = mapper.mapperRequest(personId);
        SoftTokenDataEnrollmentMWResponse mwResponse = provider.getDataEnrollment(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public List<SoftTokenQuestionEnrollmentResponse> getQuestionEnrollment(String personId) throws IOException {
        SoftTokenMWRequest mwRequest = mapper.mapperRequest(personId);
        SoftTokenQuestionEnrollmentMWResponse mwResponse = provider.getQuestionEnrollment(mwRequest);
        return new ArrayList<>(mapper.convertResponse(mwResponse));
    }

    @Override
    public SoftTokenValidationEnrollmentResponse getValidationEnrollment(String personId) throws IOException {
        SoftTokenMWRequest mwRequest = mapper.mapperRequest(personId);
        SoftTokenValidationEnrollmentMWResponse mwResponse = provider.getValidationEnrollment(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

}
