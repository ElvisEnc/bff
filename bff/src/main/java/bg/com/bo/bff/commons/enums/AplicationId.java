package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AplicationId {

    GANAMOVIL("2", "GANAMOVIL");

    private final String code;
    private final String description;
}
