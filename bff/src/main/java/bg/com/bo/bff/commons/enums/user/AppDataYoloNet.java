package bg.com.bo.bff.commons.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppDataYoloNet {
    CODIGO_EXITO("COD000"),
    COD_IDIOMA("1"),
    COD_ROL("7"),
    COD_APP("20"),
    SECOND_FACTOR("");

    private final String value;
}
