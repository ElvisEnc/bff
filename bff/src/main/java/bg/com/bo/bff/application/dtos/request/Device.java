package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@lombok.Data
public class Device {

    @Schema(example = "mw8998-002.0069.00", description = "Número de version del bootloader del sistema del dispotivo.")
    private String boot;

    @NotBlank
    @Schema(example = "FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F9", description = "ID unico del dispositivo.")
    private String uniqueId;

    @Schema(example = "iPhone7.2", description = "Nombre del código del sistema operativo.")
    private String soCodeName;

    @Schema(example = "iPhone OS", description = "Nombre del OS del dispositivo.")
    private String systemName;

    @Schema(example = "11.0", description = "Version del OS del dispositivo.")
    private String systemVersion;

    @Schema(example = "12A269", description = "Build number del sistema operativo.")
    private String systemBuildId;

    @Schema(example = "Mozilla/5.0")
    private String userAgent;

    @Schema(example = "123-12-3-12-23", description = "Momento en el tiempo en el que la aplicacion fue instalada por primera vez, en milisegundos.")
    private String firstInstallTime;

    @Schema(example = "1", description = "Especifica si la aplicación se esta ejecutando en modo debug.")
    private String debug;

    @Schema(example = "123324566", description = "Especifica si la aplicacion se esta ejecutando en un emulador.")
    private String emulator;

    @Schema(example = "12.324566", description = "Coordenada X")
    private String geoPositionX;

    @Schema(example = "12.24566", description = "Coordenada Y")
    private String geoPositionY;
}
