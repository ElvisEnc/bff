package bg.com.bo.bff.providers.dtos.response.softtoken;

import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenDataEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenQuestionEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenValidationEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.softtoken.mw.SoftTokenWelcomeMWResponse;

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

    public static SoftTokenValidationEnrollmentMWResponse withDefaultValidate() {
        return SoftTokenValidationEnrollmentMWResponse.builder()
                .codeError("COD000")
                .status("PINDIG002")
                .build();
    }

    public static SoftTokenValidationEnrollmentMWResponse withDefaultNotValidate() {
        return SoftTokenValidationEnrollmentMWResponse.builder()
                .codeError("COD000")
                .status("PINDIG003")
                .build();
    }
}
