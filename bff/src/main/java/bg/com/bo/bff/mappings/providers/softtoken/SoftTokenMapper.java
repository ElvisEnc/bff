package bg.com.bo.bff.mappings.providers.softtoken;

import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenDataEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenQuestionEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenValidationEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.softtoken.SoftTokenWelcomeResponse;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.SoftTokenMWRequest;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenValidationEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class SoftTokenMapper implements ISoftTokenMapper {

    @Override
    public SoftTokenMWRequest mapperRequest(String personId) {
        return SoftTokenMWRequest.builder()
                .codPerson(Integer.valueOf(personId))
                .codApplication(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codCanal(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codLanguage(1)
                .build();
    }

    @Override
    public SoftTokenWelcomeResponse convertResponse(SoftTokenWelcomeMWResponse mwResponse) {
        return SoftTokenWelcomeResponse.builder()
                .message(mwResponse.getMessage())
                .build();
    }

    @Override
    public SoftTokenDataEnrollmentResponse convertResponse(SoftTokenDataEnrollmentMWResponse mwResponse) {
        SoftTokenDataEnrollmentMWResponse.DataEnrollmentSoftToken dataResponse = Optional.ofNullable(mwResponse.getData())
                .flatMap(dataList -> dataList.stream().findFirst())
                .orElseGet(SoftTokenDataEnrollmentMWResponse.DataEnrollmentSoftToken::new);

        return SoftTokenDataEnrollmentResponse.builder()
                .telephone(dataResponse.getTelephone())
                .email(dataResponse.getEmail())
                .build();
    }

    @Override
    public List<SoftTokenQuestionEnrollmentResponse> convertResponse(SoftTokenQuestionEnrollmentMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> SoftTokenQuestionEnrollmentResponse.builder()
                        .question(mw.getQuestion())
                        .format(mw.getFormat())
                        .textHelp(mw.getTextHelp())
                        .build())
                .toList();
    }

    @Override
    public SoftTokenValidationEnrollmentResponse convertResponse(SoftTokenValidationEnrollmentMWResponse mwResponse) {
        String status = STATUS_MESSAGES.getOrDefault(mwResponse.getStatus(), "Unknown status");
        return SoftTokenValidationEnrollmentResponse.builder()
                .status(status)
                .build();
    }

    private static final Map<String, String> STATUS_MESSAGES = Map.of(
            SoftTokenMiddlewareResponse.ENROLLMENT.getCode(), SoftTokenMiddlewareResponse.ENROLLMENT.getMessage(),
            SoftTokenMiddlewareResponse.NOT_ENROLLMENT.getCode(), SoftTokenMiddlewareResponse.NOT_ENROLLMENT.getMessage()
    );
}
