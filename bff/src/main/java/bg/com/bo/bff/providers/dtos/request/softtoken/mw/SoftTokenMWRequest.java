package bg.com.bo.bff.providers.dtos.request.softtoken.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoftTokenMWRequest {
    private Integer codPerson;
    private Integer codApplication;
    private Integer codCanal;
    private Integer codLanguage;
    private String imei;
    private String didBga;
    private String ksBga;
    private String operatingSystem;
}
