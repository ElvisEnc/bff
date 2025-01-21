package bg.com.bo.bff.mappings.providers.softtoken;

import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenValidationEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenMWRequest;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenValidationEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;

import java.util.List;


public interface ISoftTokenMapper {

    SoftTokenMWRequest mapperRequest(String personId);

    SoftTokenWelcomeResponse convertResponse(SoftTokenWelcomeMWResponse mwResponse);

    SoftTokenDataEnrollmentResponse convertResponse(SoftTokenDataEnrollmentMWResponse mwResponse);

    List<SoftTokenQuestionEnrollmentResponse> convertResponse(SoftTokenQuestionEnrollmentMWResponse mwResponse);

    SoftTokenValidationEnrollmentResponse convertResponse(SoftTokenValidationEnrollmentMWResponse mwResponse);
}
