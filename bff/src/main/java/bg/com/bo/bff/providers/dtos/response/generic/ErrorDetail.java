package bg.com.bo.bff.providers.dtos.response.generic;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ErrorDetail {
    private String code;
    private String description;
    private String messageTechnical;

    public ErrorDetail(String code, String description) {
        this.code = code;
        this.description = description;
    }
    public ErrorDetail(String code, String description, String messageTechnical) {
        this.code = code;
        this.description = description;
        this.messageTechnical = messageTechnical;
    }
}
