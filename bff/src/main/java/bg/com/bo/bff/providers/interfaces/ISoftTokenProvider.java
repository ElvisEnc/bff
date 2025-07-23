package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.softtoken.mw.*;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.*;

import java.io.IOException;

public interface ISoftTokenProvider {

    SoftTokenWelcomeMWResponse getWelcomeMessage(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenDataEnrollmentMWResponse getDataEnrollment(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenQuestionEnrollmentMWResponse getQuestionEnrollment(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenEnrollmentMWResponse getValidationEnrollment(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenEnrollmentMWResponse postCodeEnrollment(SoftTokenSentCodeMWRequest mwRequest) throws IOException;

    SoftTokenEnrollmentMWResponse validateCodeEnrollment(SoftTokenValidateCodeMWRequest mwRequest) throws IOException;

    SoftTokenEnrollmentMWResponse validateQuestionSecurity(SoftTokenValidateQuestionMWRequest mwRequest) throws IOException;

    SoftTokenObtainParametersMWResponse getParameters(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenCodeTokenMWResponse postRegistrationToken(SoftTokenRegistrationTokenMWRequest mwRequest) throws IOException;

    SoftTokenCodeTokenMWResponse getRegistrationValidation(SofTokenValidateMWRequest mwRequest) throws IOException;

    SoftTokenGenerateTokenMWResponse postTokenGenerate(SofTokenGenerateTokenMWRequest mwRequest) throws IOException;

    SoftTokenCodeTokenMWResponse postEnrollment(SofTokenEnrollmentMWRequest mwRequest) throws IOException;

    SoftTokenCodeTokenMWResponse validationToken(SoftTokenValidateTokenMWRequest mwRequest) throws IOException;

}
