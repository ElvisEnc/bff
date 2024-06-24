package bg.com.bo.bff.application.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubCategoryCitiesMWResponse {

    private List<CityMW> data;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CityMW{
        public int id;
        public String name;
    }
}
