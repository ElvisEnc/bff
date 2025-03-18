package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.softtoken.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ISoftTokenService {

    SoftTokenWelcomeResponse getWelcomeMessage(String personId) throws IOException;

    SoftTokenDataEnrollmentResponse getDataEnrollment(String personId) throws IOException;

    List<SoftTokenQuestionEnrollmentResponse> getQuestionEnrollment(String personId) throws IOException;

    GenericResponse getValidationEnrollment(String personId) throws IOException;

    GenericResponse postCodeEnrollment(String personId, SoftTokenCodeEnrollmentRequest request) throws IOException;

    GenericResponse validateCodeEnrollment(String personId, SoftTokenValidateCodeEnrollmentRequest request) throws IOException;

    GenericResponse validateQuestionSecurity(String personId, String deviceModel, SoftTokenValidationQuestionRequest request) throws IOException;

    SoftTokenObtainParametersResponse getParameters(String personId) throws IOException;

    GenericResponse postRegistrationToken(String personId, SoftTokenCodeTokenRequest request) throws IOException;

    GenericResponse getRegistrationValidation(String personId) throws IOException;

    SoftTokenGenerateTokenResponse postTokenGenerate(String personId) throws IOException;

    GenericResponse postEnrollment(String personId, String deviceModel, SoftTokenEnrollmentRequest request) throws IOException;

    GenericResponse validationToken(String personId,  SoftTokenCodeTokenRequest request) throws IOException;

}
