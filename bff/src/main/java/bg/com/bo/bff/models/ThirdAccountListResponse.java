package bg.com.bo.bff.models;

import lombok.ToString;

import java.util.List;

@lombok.Data
@ToString
public class ThirdAccountListResponse {
    private List<ThirdAccount> data;
}
