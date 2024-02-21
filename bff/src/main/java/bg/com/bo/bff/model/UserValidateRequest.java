package bg.com.bo.bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@lombok.Data
public class UserValidateRequest {
    @JsonProperty(value = "serviceID")
    private String serviceID;
    @JsonProperty(value = "pStrNombreUsuario")
    private String pStrNombreUsuario;
    @JsonProperty(value = "pStrClave")
    private String pStrClave;
    @JsonProperty(value = "pStrCanal")
    private String pStrCanal;
    @JsonProperty(value = "pStrImei")
    private String pStrImei;
    @JsonProperty(value = "pStrModelo")
    private String pStrModelo;
    @JsonProperty(value = "pStrSistemaOperativo")
    private String pStrSistemaOperativo;
    @JsonProperty(value = "did")
    private String did;
    @JsonProperty(value = "rsid")
    private String rsid;
    @JsonProperty(value = "version")
    private String version;
    @JsonProperty(value = "didbga")
    private String didbga;
    @JsonProperty(value = "pStrKsBga")
    private String pStrKsBga;
}
