package bg.com.bo.bff.application.dtos.response;



import bg.com.bo.bff.application.dtos.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

@Data
@AllArgsConstructor
public class BanksResponse {
    private List<Bank> data;

}


