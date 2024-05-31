package bg.com.bo.bff.providers.dtos.response.account.ach;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchAccountMW {
             private String idList;
             private String personId;
             private String bankCode;
             private String bankName;
             private String branchCode;
             private String accountTypeCode;
             private String accountNumber;
             private String documentNumber;
             private String holderName;
             private String accountNickname;
             private String isFavorite;
}
