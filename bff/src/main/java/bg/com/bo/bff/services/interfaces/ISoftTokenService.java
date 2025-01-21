package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenValidationEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ISoftTokenService {

    SoftTokenWelcomeResponse getWelcomeMessage(String personId) throws IOException;

    SoftTokenDataEnrollmentResponse getDataEnrollment(String personId) throws IOException;

    List<SoftTokenQuestionEnrollmentResponse> getQuestionEnrollment(String personId) throws IOException;

    SoftTokenValidationEnrollmentResponse getValidationEnrollment(String personId) throws IOException;
}
