package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppDataYoloNet {
    COD_IDIOMA("1"),
    COD_ROL("7"),
    COD_APP("20"),
    SECOND_FACTOR("");

    private final String value;
}
