package bg.com.bo.bff.commons.enums;

@lombok.Getter
@lombok.AllArgsConstructor
public enum CategoryError {
    AUTH(1100),
    ENCRYPTION(1101),
    UNHANDLED(1000),
    INVALID_FORMAT(1200),
    MW_INTERNAL_FAILURE(1300),
    MW_FAIL_RESPONSE(1400),
    MW_UNEXPECTED_FORMAT(1500),
    NO_MAPPED_MW_ERROR(1501),
    BFF_COMPONENT_UNAVAILABLE(1600);

    private final int categoryId;
}
