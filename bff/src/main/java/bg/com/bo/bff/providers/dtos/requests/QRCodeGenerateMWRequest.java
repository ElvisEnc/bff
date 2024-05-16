package bg.com.bo.bff.providers.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class QRCodeGenerateMWRequest {
    private String companyName;

    private String amount;

    private String currency;

    private String reference;

    private String typeDueDate;

    private String codService;

    private String singleUse;

    private String accountNumber;

    private String field;

    private String serialNumber;

    private String operationType;

    private String personId;

    private String userRegister;
    private String typeReturn;
    private String formatImage;
    private OwnerAccount ownerAccount;
    public void setOwnerAccount(String schemeName, String personId){
        this.setOwnerAccount(new OwnerAccount(schemeName,
                personId));
    }

}


  class OwnerAccount {
    public OwnerAccount(String schemeName, String personId) {
        this.schemeName = schemeName;
        this.personId = personId;
    }

    private String schemeName;
    private String personId;

      public String getSchemeName() {
          return schemeName;
      }

      public String getPersonId() {
          return personId;
      }
  }

