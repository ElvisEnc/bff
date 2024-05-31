package bg.com.bo.bff.application.dtos.request.qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TargetAccount {
    private String id;
    private String secondaryIdentification;
    private String name;
}
