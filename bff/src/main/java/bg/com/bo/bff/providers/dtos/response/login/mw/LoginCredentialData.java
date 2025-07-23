package bg.com.bo.bff.providers.dtos.response.login.mw;

import lombok.Data;
import java.util.List;

@Data
public class LoginCredentialData {
    private String securityImage;
    private String secondFactor;
    private String keyChange;
    private String keyChangeMessage;
    private Integer userEnrollmentId;
    private String holderName;
    private List<Role> roleList;
    private String lastConnectionDate;
    private String codError;
    private String desError;

    @Data
    public static class Role {
        private Integer rolePersonId;
        private String description;
        private Integer personId;
        private Integer relatedPersonId;
        private String companyName;
        private String name;
        private Integer roleCode;
        private Integer updateUse;
    }
}
