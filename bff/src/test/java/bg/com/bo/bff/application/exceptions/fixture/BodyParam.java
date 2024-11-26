package bg.com.bo.bff.application.exceptions.fixture;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class BodyParam {
    @NotBlank()
    @Size(min = 2, max = 19)
    private String stringParam;

    @Min(value = 1)
    private Integer decimalParam;
}