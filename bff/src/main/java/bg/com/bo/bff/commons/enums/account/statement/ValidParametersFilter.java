package bg.com.bo.bff.commons.enums.account.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ValidParametersFilter {
    TOACCOUNTNUMBER,
    AMOUNT,
    DESCRIPTION;

    private static boolean isValid(String param) {
        for (ValidParametersFilter p : ValidParametersFilter.values()) {
            if (p.name().equalsIgnoreCase(param)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidParams(List<String> validParameters) {
        for (String param : validParameters) {
            if (!isValid(param)) {
                return false;
            }
        }
        return true;
    }
}
