package bg.com.bo.bff.provider.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import bg.com.bo.bff.controllers.request.IngresoRequest;
import bg.com.bo.bff.controllers.request.LoginBiometricRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "IngresoRequestDTO")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IngresoRequestDTO {

    @JsonProperty(value = "pStrNombreUsuario")
    private String userName;

    @JsonProperty(value = "pStrClave")
    private String clave;

    @JsonProperty(value = "pStrCanal")
    private String canal;

    @JsonProperty(value = "pStrImei")
    private String imei;

    @JsonProperty(value = "pStrModelo")
    private String model;

    @JsonProperty(value = "pStrSistemaOperativo")
    private String systemOperative;

    @JsonProperty(value = "did")
    private String did;

    @JsonProperty(value = "rsid")
    private String rSid;

    @JsonProperty(value = "aver")
    private String aver;

    @JsonProperty(value = "didbga")
    private String didBga;

    @JsonProperty(value = "pStrKsBga")
    private String ksBga;

    public static IngresoRequestDTO toParse(IngresoRequest request) {
        return IngresoRequestDTO.builder()
                .userName(request.getUserName())
                .clave(request.getClave())
                .canal(request.getCanal())
                .imei(request.getImei())
                .model(request.getModel())
                .systemOperative(request.getSystemOperative())
                .did(request.getDid())
                .rSid(request.getRSid())
                .aver(request.getAver())
                .didBga(request.getDidBga())
                .ksBga(request.getKsBga())
                .build();
    }

}
