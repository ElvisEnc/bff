package bg.com.bo.bff.provider.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@ToString
public class ErrorDetailResponse {
    private String code;
    private String description;

    public ErrorDetailResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static List<ErrorDetail> toErrorDetail(Set<ErrorDetailResponse> errorDetailResponseList) {
        return errorDetailResponseList.stream()
                .map(errorDetailResponse -> new ErrorDetail(
                        errorDetailResponse.getCode(),
                        errorDetailResponse.getDescription()
                ))
                .toList();
    }
}
