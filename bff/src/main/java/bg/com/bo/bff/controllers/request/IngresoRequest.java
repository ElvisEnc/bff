package bg.com.bo.bff.controllers.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName(value = "Ingreso GanaMovil")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Ingreso GanaMovil", description = "Ingreso GanaMovil")
public class IngresoRequest {

    private String userName;


    private String clave;

    private String canal;

    private String imei;


    private String model;


    private String systemOperative;


    private String did;

    private String rSid;

    private String aver;

    private String didBga;

    private String ksBga;


}
