package bg.com.bo.bff.application.dtos.response.destination.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BanksResponse {
    private List<Bank> data;
}


