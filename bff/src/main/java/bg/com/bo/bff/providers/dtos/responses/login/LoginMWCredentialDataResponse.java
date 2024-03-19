package bg.com.bo.bff.providers.dtos.responses.login;

import java.util.List;

@lombok.Data
public class LoginMWCredentialDataResponse {
    private String securityImage;
    private String secondFactor;
    private String keyChange;
    private String keyChangeMessage;
    private Integer userDeviceId;
    private List<Role> roleList;
    private String lastConnectionDate;
    private String codError;
    private String desError;

    @lombok.Data
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
