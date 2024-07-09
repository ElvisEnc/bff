package bg.com.bo.bff.providers.dtos.request.personal.information.affiliation;

public record DataRegisterServiceAffiliationMW(
        String label,
        String value,
        String mandatory,
        String edit,
        String group,
        String description,
        String code
) {
}
