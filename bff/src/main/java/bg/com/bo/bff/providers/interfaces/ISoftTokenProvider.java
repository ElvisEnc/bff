package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenMWRequest;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenValidationEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;

import java.io.IOException;

public interface ISoftTokenProvider {

    SoftTokenWelcomeMWResponse getWelcomeMessage(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenDataEnrollmentMWResponse getDataEnrollment(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenQuestionEnrollmentMWResponse getQuestionEnrollment(SoftTokenMWRequest mwRequest) throws IOException;

    SoftTokenValidationEnrollmentMWResponse getValidationEnrollment(SoftTokenMWRequest mwRequest) throws IOException;

}
