package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.softtoken.SoftTokenCodeEnrollmentRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenValidationEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.user.AppCodeResponseNet;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.mappings.providers.softtoken.ISoftTokenMapper;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenMWRequest;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenSentCodeMWRequest;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;
import bg.com.bo.bff.providers.interfaces.ISoftTokenProvider;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.remittance.RemittanceMiddlewareResponse;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareResponse;
import bg.com.bo.bff.services.interfaces.ISoftTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
    public GenericResponse getValidationEnrollment(String personId) throws IOException {
        SoftTokenMWRequest mwRequest = mapper.mapperRequest(personId);
        SoftTokenEnrollmentMWResponse mwResponse = provider.getValidationEnrollment(mwRequest);
        if (mwResponse.getStatus().equals(SoftTokenMiddlewareResponse.ENROLLMENT.getCode())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.ENROLLMENT);
        } else if (mwResponse.getStatus().equals(SoftTokenMiddlewareResponse.NOT_ENROLLMENT.getCode())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.NOT_ENROLLMENT);
        }
        throw new GenericException(SoftTokenMiddlewareError.ERROR_CODE_UNKNOWN);
    }

    @Override
    public GenericResponse postCodeEnrollment(String personId, SoftTokenCodeEnrollmentRequest request) throws IOException {
        if ((request.getEmail() == null || request.getEmail().isEmpty()) &&
                (request.getPhone() == null || request.getPhone().isEmpty())) {
            throw new GenericException(SoftTokenMiddlewareError.PINDIG030);
        }
        SoftTokenSentCodeMWRequest mwRequest = mapper.mapperRequest(personId, request);
        SoftTokenEnrollmentMWResponse mwResponse = provider.postCodeEnrollment(mwRequest);
        if (mwResponse.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.CODE_SENT);
        }
        throw new GenericException(SoftTokenMiddlewareError.ERROR_CODE_UNKNOWN);
    }

}
