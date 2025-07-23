package bg.com.bo.bff.mappings.providers.softtoken;

import bg.com.bo.bff.application.dtos.request.softtoken.*;
import bg.com.bo.bff.application.dtos.response.softtoken.*;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.*;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.*;

import java.util.List;


public interface ISoftTokenMapper {

    SoftTokenMWRequest mapperRequest(String personId);

    SoftTokenSentCodeMWRequest mapperRequest(String personId, SoftTokenCodeEnrollmentRequest request);

    SoftTokenValidateCodeMWRequest mapperRequest(String personId, SoftTokenValidateCodeEnrollmentRequest request);

    SoftTokenValidateQuestionMWRequest mapperRequest(String personId, String deviceModel, SoftTokenValidationQuestionRequest request);

    SoftTokenMWRequest mapperRequestST(String personId);

    SoftTokenRegistrationTokenMWRequest mapperRequest(String personId, SoftTokenCodeTokenRequest request);

    SofTokenValidateMWRequest mapperRequestValidate(String personId);

    SofTokenGenerateTokenMWRequest mapperRequestGenerate(String personId);

    SofTokenEnrollmentMWRequest mapperRequestEnrollment(String personId, String deviceModel, SoftTokenEnrollmentRequest request);

    SoftTokenValidateTokenMWRequest mapperRequestToken(String personId, SoftTokenCodeTokenRequest request);

    SoftTokenWelcomeResponse convertResponse(SoftTokenWelcomeMWResponse mwResponse);

    SoftTokenDataEnrollmentResponse convertResponse(SoftTokenDataEnrollmentMWResponse mwResponse);

    List<SoftTokenQuestionEnrollmentResponse> convertResponse(SoftTokenQuestionEnrollmentMWResponse mwResponse);

    SoftTokenObtainParametersResponse convertResponse(SoftTokenObtainParametersMWResponse mwResponse);

    SoftTokenGenerateTokenResponse convertResponse(SoftTokenGenerateTokenMWResponse mwResponse);


}
