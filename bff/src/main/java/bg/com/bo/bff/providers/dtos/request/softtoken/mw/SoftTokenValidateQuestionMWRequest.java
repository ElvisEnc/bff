package bg.com.bo.bff.providers.dtos.request.softtoken.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoftTokenValidateQuestionMWRequest {
    private Integer codPerson;
    private Integer codApplication;
    private Integer codCanal;
    private Integer codLanguage;
    private String imei;
    private String didBga;
    private String ksBga;
    private String operatingSystem;
    private String answerQuestionSecurityEnrollment;
    private String model;
}
