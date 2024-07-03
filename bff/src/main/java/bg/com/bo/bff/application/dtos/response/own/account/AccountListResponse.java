package bg.com.bo.bff.application.dtos.response.own.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountListResponse {
    private List<Account> data;
}
