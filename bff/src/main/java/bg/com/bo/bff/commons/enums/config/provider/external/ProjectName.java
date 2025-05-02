package bg.com.bo.bff.commons.enums.config.provider.external;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectName {
    LOYALTY_MANAGER(" /lealtad", "LOYALTY MANAGER" ),
    CRYPTOCURRENCY_MANAGER("/empresas/criptoactivos-mdw", "CRYPTOCURRENCY MANAGER" )
    ;

    private final String name;
    private final String description;
}
