package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationId {
    GANAMOVIL("2", "GANAMOVIL");
    private final String code;
    private final String description;
}
