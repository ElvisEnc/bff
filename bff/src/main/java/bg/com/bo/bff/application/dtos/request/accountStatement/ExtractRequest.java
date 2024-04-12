package bg.com.bo.bff.application.dtos.request.accountStatement;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class ExtractRequest {
    @Valid
    private ExtractFilter filters;
}
