package bg.com.bo.bff.commons.enums.attention.points;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum SubTypeAttentionPoints {
    DRIVE_THRU_BANK("DRIVE_THRU_BANK", 'B', "AutoBanco"),
    DEPOSITARY_DRIVE_THRU_BANK("DEPOSITARY_DRIVE_THRU_BANK", 'L', "Depositario AutoBanco"),
    KIOSK("KIOSK", 'K', "Kiosco"),
    ATM("ATM", 'T', "ATM"),
    ATM_2("ATM", 'I', "ATM"),
    AGENCY("AGENCY", 'A', "Agencia"),
    DEPOSITARY("DEPOSITARY", 'E', "Depositario"),
    DEPOSITARY_HANDICAPPED("DEPOSITARY_HANDICAPPED", 'J', "Depositario Capacidades diferentes"),
    AGENCY_2("AGENCY", 'D', "Agencia");

    private final String code;
    private final char subType;
    private final String description;

    private static final Map<Character, String> SUB_TYPE_MAP = new HashMap<>();

    static {
        for (SubTypeAttentionPoints subtype : SubTypeAttentionPoints.values()) {
            SUB_TYPE_MAP.put(subtype.subType, subtype.code);
        }
    }

    public static String getCodeBySubType(char subType) {
        return SUB_TYPE_MAP.getOrDefault(subType, "ATM");
    }
}
