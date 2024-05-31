package bg.com.bo.bff.providers.dtos.request.loginagn;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class ReactDeviceIdentificatorRequest {
    @JsonProperty("idUnico")
    private String uniqueId;
    @JsonProperty("codBootloader")
    private String bootLoader;
    @JsonProperty("osCodName")
    private String osCode;
    private String osName;
    private String osVersion;
    @JsonProperty("osBuildID")
    private String osBuildId;
    private String userAgent;
    private Long firstInstallTime;
    private Integer debug;
    private Integer emulator;
}
