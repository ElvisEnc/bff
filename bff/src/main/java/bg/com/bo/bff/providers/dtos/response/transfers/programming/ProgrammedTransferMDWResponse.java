package bg.com.bo.bff.providers.dtos.response.transfers.programming;

import bg.com.bo.bff.application.dtos.response.transfers.programming.ProgrammedTransfersResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammedTransferMDWResponse {

    private List<TransferObj> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TransferObj{
        private int transferId;
        private int personId;
        private int originJtsOid;
        private String destinationAccount;
        private BigDecimal amount;
        private int currency;
        private String transferType;
        private String description;
        private String frequencyCode;
        private String initDate;
        private String endDate;
        private String userRegistry;
        private String registryDate;
        private String userModification;
        private String modificationDate;
        private int tzLock;
        private int destinationJtsOid;
        private int listCode;
        private String codEif;
        private String destinationFounds;
        private String originFounds;
        private String reason;
        private String status;
        private String destinationName;
        private String bankName;
        private String uifControl;
        private int feeNumber;
    }

}
