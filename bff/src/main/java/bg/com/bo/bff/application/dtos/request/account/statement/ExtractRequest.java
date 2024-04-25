package bg.com.bo.bff.application.dtos.request.account.statement;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractRequest {
    @Valid
    private ExtractFilter filters;
}
