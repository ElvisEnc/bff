package bg.com.bo.bff.application.dtos.response.dpf;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class DpfDataResponse {
    @Schema(example = "1234567", description = "Este es el Numero de DPF")
    private String numDPF;
    @Schema(example = "BGA-201-311040-0-0", description = "Este es el Identificador de dpf BGA")
    private String numDpfBGA;
    @Schema(example = "INSTITUCION FINANCIERA", description = "Este es el Nombre del cliente")
    private String clientName;
    @Schema(example = "50000", description = "Este es el Capital del DPF")
    private String capital;
    @Schema(example = "1234567", description = "Este es el intereses del DPF")
    private String interes;
    @Schema(example = "1234567", description = "Este es el Total ganado del DPF")
    private String total;
    @Schema(example = "068", description = "Este es el Código de la moneda del DPF")
    private String codeCurrency;
    @Schema(example = "2016-01-01", description = "Este es la Fecha de alta")
    private String dischargeDate;
    @Schema(example = "2026-04-15", description = "Este es la Fecha de vencimiento del DPF")
    private String expirationDate;
    @Schema(example = "5412", description = "Este es el Plazo del DPF")
    private String term;
    @Schema(example = "3.5", description = "Este es la Tasa del DPF")
    private String rate;
    @Schema(example = "AL VENCIMIENTO", description = "Este es la Frecuencia de pago del DPF")
    private String paymentFrequency;
}
