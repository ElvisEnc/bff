package bg.com.bo.service.template.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@lombok.Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Example {
    @NotNull
    private Integer id;
    @NotBlank
    private String description;
}
