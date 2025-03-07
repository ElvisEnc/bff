package bg.com.bo.bff.providers.dtos.response.softtoken;

import bg.com.bo.bff.providers.dtos.response.softtoken.mw.*;

import java.util.ArrayList;
import java.util.List;

public class SoftTokenMWResponseFixture {
    public static SoftTokenWelcomeMWResponse withDefaultWelcome() {
        return SoftTokenWelcomeMWResponse.builder()
                .codeError("COD000")
                .message("ok")
                .build();
    }

    public static List<SoftTokenDataEnrollmentMWResponse.DataEnrollmentSoftToken> dataEnrollment() {
        SoftTokenDataEnrollmentMWResponse.DataEnrollmentSoftToken softTokenData =
                SoftTokenDataEnrollmentMWResponse.DataEnrollmentSoftToken.builder()
                        .telephone("74114784")
                        .email("tests@test.com")
                        .build();
        return List.of(softTokenData);
    }

    public static SoftTokenDataEnrollmentMWResponse withDefaultDataEnrollment() {
        List<SoftTokenDataEnrollmentMWResponse.DataEnrollmentSoftToken> data = dataEnrollment();

        return SoftTokenDataEnrollmentMWResponse.builder()
                .codeError("COD000")
                .data(data)
                .build();
    }

    public static SoftTokenDataEnrollmentMWResponse withDefaultDataEnrollmentNull() {
        List<SoftTokenDataEnrollmentMWResponse.DataEnrollmentSoftToken> data = new ArrayList<>();

        return SoftTokenDataEnrollmentMWResponse.builder()
                .codeError("COD000")
                .data(data)
                .build();
    }

    public static List<SoftTokenQuestionEnrollmentMWResponse.SoftTokenQuestionEnrollmentMW> dataQuestion() {
        SoftTokenQuestionEnrollmentMWResponse.SoftTokenQuestionEnrollmentMW softTokenData =
                SoftTokenQuestionEnrollmentMWResponse.SoftTokenQuestionEnrollmentMW.builder()
                        .question("test")
                        .format("date")
                        .textHelp("test")
                        .build();
        return List.of(softTokenData);
    }


    public static SoftTokenQuestionEnrollmentMWResponse withDefaultQuestion() {
        return SoftTokenQuestionEnrollmentMWResponse.builder()
                .data(dataQuestion())
                .build();
    }

    public static SoftTokenQuestionEnrollmentMWResponse withDefaultQuestionNull() {
        return SoftTokenQuestionEnrollmentMWResponse.builder()
                .data(null)
                .build();
    }

    public static SoftTokenEnrollmentMWResponse withDefaultValidate() {
        return SoftTokenEnrollmentMWResponse.builder()
                .codeError("COD000")
                .status("PINDIG002")
                .build();
    }

    public static SoftTokenEnrollmentMWResponse withDefaultNotValidate() {
        return SoftTokenEnrollmentMWResponse.builder()
                .codeError("COD000")
                .status("PINDIG003")
                .build();
    }

    public static SoftTokenEnrollmentMWResponse withDefaultError() {
        return SoftTokenEnrollmentMWResponse.builder()
                .codeError("COD003")
                .status("error")
                .build();
    }

    public static SoftTokenEnrollmentMWResponse withDefaultValidationQuestion() {
        return SoftTokenEnrollmentMWResponse.builder()
                .codeError("COD000")
                .status("ok")
                .build();
    }

    public static SoftTokenObtainParametersMWResponse withDefaultParameter() {
        return SoftTokenObtainParametersMWResponse.builder()
                .codeError("COD000")
                .tokenSize(6)
                .tokenDuration(30)
                .processDate("test")
                .serialNumber("744111")
                .build();
    }

    public static SoftTokenCodeTokenMWResponse withDefaultCod000() {
        return SoftTokenCodeTokenMWResponse.builder()
                .codeError("COD000")
                .build();
    }

    public static SoftTokenCodeTokenMWResponse withDefaultErrorToken() {
        return SoftTokenCodeTokenMWResponse.builder()
                .codeError("COD003")
                .build();
    }

    public static SoftTokenGenerateTokenMWResponse withDefaultGenerateToken() {
        return SoftTokenGenerateTokenMWResponse.builder()
                .codeError("COD000")
                .codeToken("1234")
                .durationToken(30)
                .build();
    }

    public static SoftTokenCodeTokenMWResponse withDefaultPINDIG003() {
        return SoftTokenCodeTokenMWResponse.builder()
                .codeError("PINDIG003")
                .build();
    }

    public static SoftTokenCodeTokenMWResponse withDefaultPINDIG002() {
        return SoftTokenCodeTokenMWResponse.builder()
                .codeError("PINDIG002")
                .build();
    }

}
