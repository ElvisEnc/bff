package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.softtoken.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.*;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.user.AppCodeResponseNet;
import bg.com.bo.bff.mappings.providers.softtoken.ISoftTokenMapper;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.*;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.*;
import bg.com.bo.bff.providers.interfaces.ISoftTokenProvider;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareResponse;
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
    public GenericResponse getValidationEnrollment(String personId) throws IOException {
        SoftTokenMWRequest mwRequest = mapper.mapperRequest(personId);
        SoftTokenEnrollmentMWResponse mwResponse = provider.getValidationEnrollment(mwRequest);
        if (mwResponse.getStatus().equals(SoftTokenMiddlewareError.PINDIG002.getCodeMiddleware())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.ENROLLMENT);
        }
        return GenericResponse.instance(SoftTokenMiddlewareResponse.NOT_ENROLLED);
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
        throw new GenericException(SoftTokenMiddlewareError.PINDIG014);
    }

    @Override
    public GenericResponse validateCodeEnrollment(String personId, SoftTokenValidateCodeEnrollmentRequest request) throws IOException {
        SoftTokenValidateCodeMWRequest mwRequest = mapper.mapperRequest(personId, request);
        SoftTokenEnrollmentMWResponse mwResponse = provider.validateCodeEnrollment(mwRequest);
        if (mwResponse.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.VALIDATED);
        }
        throw new GenericException(SoftTokenMiddlewareError.PINDIG024);
    }

    @Override
    public GenericResponse validateQuestionSecurity(String personId, String deviceModel, SoftTokenValidationQuestionRequest request) throws IOException {
        SoftTokenValidateQuestionMWRequest mwRequest = mapper.mapperRequest(personId, deviceModel, request);
        SoftTokenEnrollmentMWResponse mwResponse = provider.validateQuestionSecurity(mwRequest);
        if (mwResponse.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.QUESTION_VALIDATED);
        }
        throw new GenericException(SoftTokenMiddlewareError.PINDIG027);
    }

    @Override
    public SoftTokenObtainParametersResponse getParameters(String personId) throws IOException {
        SoftTokenMWRequest mwRequest = mapper.mapperRequestST(personId);
        SoftTokenObtainParametersMWResponse mwResponse = provider.getParameters(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public GenericResponse postRegistrationToken(String personId, SoftTokenCodeTokenRequest request) throws IOException {
        SoftTokenRegistrationTokenMWRequest mwRequest = mapper.mapperRequest(personId, request);
        SoftTokenCodeTokenMWResponse mwResponse = provider.postRegistrationToken(mwRequest);
        if (mwResponse.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.REGISTERED_TOKEN);
        }
        throw new GenericException(SoftTokenMiddlewareError.PINDIG059);
    }

    @Override
    public GenericResponse getRegistrationValidation(String personId) throws IOException {
        SofTokenValidateMWRequest mwRequest = mapper.mapperRequestValidate(personId);
        SoftTokenCodeTokenMWResponse mwResponse = provider.getRegistrationValidation(mwRequest);
        if (mwResponse.getCodeError().equals(SoftTokenMiddlewareError.PINDIG002.getCodeMiddleware())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.ENROLLMENT);
        }
        return GenericResponse.instance(SoftTokenMiddlewareResponse.NOT_ENROLLED);
    }

    @Override
    public SoftTokenGenerateTokenResponse postTokenGenerate(String personId) throws IOException {
        SofTokenGenerateTokenMWRequest mwRequest = mapper.mapperRequestGenerate(personId);
        SoftTokenGenerateTokenMWResponse mwResponse = provider.postTokenGenerate(mwRequest);
        return mapper.convertResponse(mwResponse);
    }

    @Override
    public GenericResponse postEnrollment(String personId, String deviceModel, SoftTokenEnrollmentRequest request) throws IOException {
        SofTokenEnrollmentMWRequest mwRequest = mapper.mapperRequestEnrollment(personId, deviceModel, request);
        SoftTokenCodeTokenMWResponse mwResponse = provider.postEnrollment(mwRequest);
        if (mwResponse.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.ENROLLMENT);
        }
        throw new GenericException(SoftTokenMiddlewareError.PINDIG003);
    }

    @Override
    public GenericResponse validationToken(String personId, SoftTokenCodeTokenRequest request) throws IOException {
        SoftTokenValidateTokenMWRequest mwRequest = mapper.mapperRequestToken(personId, request);
        SoftTokenCodeTokenMWResponse mwResponse = provider.validationToken(mwRequest);
        if (mwResponse.getCodeError().equals(AppCodeResponseNet.SUCCESS_CODE_STRING.getValue())) {
            return GenericResponse.instance(SoftTokenMiddlewareResponse.VALIDATED_TOKEN);
        }
        throw new GenericException(SoftTokenMiddlewareError.PINDIG051);
    }


}
