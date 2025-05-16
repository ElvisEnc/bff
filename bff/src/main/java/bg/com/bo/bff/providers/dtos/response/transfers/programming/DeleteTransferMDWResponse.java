package bg.com.bo.bff.providers.dtos.response.transfers.programming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteTransferMDWResponse {

    private DeleteTransfer data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteTransfer{
        private String codError;
        private String desError;
    }

}
