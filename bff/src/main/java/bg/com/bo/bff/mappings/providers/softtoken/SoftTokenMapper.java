package bg.com.bo.bff.mappings.providers.softtoken;

import bg.com.bo.bff.application.dtos.request.softtoken.*;
import bg.com.bo.bff.application.dtos.response.softtoken.*;
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
import bg.com.bo.bff.providers.dtos.request.softtoken.mw.*;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.*;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareEnums;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component
public class SoftTokenMapper implements ISoftTokenMapper {

    @Override
    public SoftTokenMWRequest mapperRequest(String personId) {
        return SoftTokenMWRequest.builder()
                .codPerson(Integer.valueOf(personId))
                .codApplication(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codCanal(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codLanguage(SoftTokenMiddlewareEnums.CODE_LANGUAGE.getCode())
                .build();
    }

    @Override
    public SoftTokenSentCodeMWRequest mapperRequest(String personId, SoftTokenCodeEnrollmentRequest request) {
        return SoftTokenSentCodeMWRequest.builder()
                .codPerson(Integer.valueOf(personId))
                .codApplication(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codCanal(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codLanguage(SoftTokenMiddlewareEnums.CODE_LANGUAGE.getCode())
                .telephone(request.getPhone())
                .email(request.getEmail())
                .build();
    }

    @Override
    public SoftTokenValidateCodeMWRequest mapperRequest(String personId, SoftTokenValidateCodeEnrollmentRequest request) {
        return SoftTokenValidateCodeMWRequest.builder()
                .codPerson(Integer.valueOf(personId))
                .codApplication(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codCanal(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codLanguage(SoftTokenMiddlewareEnums.CODE_LANGUAGE.getCode())
                .enrollmentSecurityCode(request.getCode())
                .build();
    }

    @Override
    public SoftTokenValidateQuestionMWRequest mapperRequest(String personId, String deviceModel, SoftTokenValidationQuestionRequest request) {
        return SoftTokenValidateQuestionMWRequest.builder()
                .codPerson(Integer.valueOf(personId))
                .codApplication(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codCanal(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codLanguage(SoftTokenMiddlewareEnums.CODE_LANGUAGE.getCode())
                .answerQuestionSecurityEnrollment(request.getAnswerQuestion())
                .model(deviceModel)
                .build();
    }

    @Override
    public SoftTokenMWRequest mapperRequestST(String personId) {
        return SoftTokenMWRequest.builder()
                .codPerson(Integer.valueOf(personId))
                .codApplication(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codCanal(Integer.valueOf(CanalMW.WEB.getCanal()))
                .codLanguage(SoftTokenMiddlewareEnums.CODE_LANGUAGE.getCode())
                .build();
    }

    @Override
    public SoftTokenRegistrationTokenMWRequest mapperRequest(String personId, SoftTokenCodeTokenRequest request) {
        return SoftTokenRegistrationTokenMWRequest.builder()
                .codPerson(Integer.valueOf(personId))
                .codApplication(Integer.valueOf(CanalMW.GANAMOVIL.getCanal()))
                .codCanal(Integer.valueOf(CanalMW.WEB.getCanal()))
                .codLanguage(SoftTokenMiddlewareEnums.CODE_LANGUAGE.getCode())
                .token(request.getToken())
                .build();
    }

    @Override
    public SofTokenValidateMWRequest mapperRequestValidate(String personId) {
        return SofTokenValidateMWRequest.builder()
                .personId(personId)
                .build();
    }

    @Override
    public SofTokenGenerateTokenMWRequest mapperRequestGenerate(String personId) {
        return SofTokenGenerateTokenMWRequest.builder()
                .personId(personId)
                .codCanal(CanalMW.WEB.getCanal())
                .build();
    }

    @Override
    public SofTokenEnrollmentMWRequest mapperRequestEnrollment(String personId, String deviceModel, SoftTokenEnrollmentRequest request) {
        return SofTokenEnrollmentMWRequest.builder()
                .personId(personId)
                .deviceModel(deviceModel)
                .telephone(request.getPhone())
                .build();
    }

    @Override
    public SoftTokenValidateTokenMWRequest mapperRequestToken(String personId, SoftTokenCodeTokenRequest request) {
        return SoftTokenValidateTokenMWRequest.builder()
                .personId(personId)
                .token(request.getToken())
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
    public SoftTokenObtainParametersResponse convertResponse(SoftTokenObtainParametersMWResponse mwResponse) {
        return SoftTokenObtainParametersResponse.builder()
                .tokenSize(mwResponse.getTokenSize())
                .tokenDuration(mwResponse.getTokenDuration())
                .serialNumber(mwResponse.getSerialNumber())
                .dateProcessed(mwResponse.getProcessDate())
                .build();
    }

    @Override
    public SoftTokenGenerateTokenResponse convertResponse(SoftTokenGenerateTokenMWResponse mwResponse) {
        return SoftTokenGenerateTokenResponse.builder()
                .token(mwResponse.getCodeToken())
                .tokenDuration(mwResponse.getDurationToken())
                .build();
    }

}
