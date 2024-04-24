package bg.com.bo.bff.providers.mappings.destination.account;

import bg.com.bo.bff.application.dtos.response.BranchOfficeDataResponse;
import bg.com.bo.bff.application.dtos.response.BranchOfficeResponse;
import bg.com.bo.bff.providers.dtos.responses.BranchOfficeMWResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DestinationAccountMapper implements IDestinationAccountMapper {
    public BranchOfficeResponse mapToBranchOfficeResponse(BranchOfficeMWResponse mwResponse) {
        List<BranchOfficeDataResponse> dataList = new ArrayList<>();
        if (mwResponse != null && mwResponse.getData() != null) {
            for (BranchOfficeMWResponse.BranchOfficeMWData.BranchOfficeArray branchOfficeArray : mwResponse.getData().getResponse()) {
                BranchOfficeDataResponse data = BranchOfficeDataResponse.builder()
                        .id(branchOfficeArray.getBranchCode())
                        .description(branchOfficeArray.getDescription())
                        .build();
                dataList.add(data);
            }
        }
        return BranchOfficeResponse.builder()
                .data(dataList)
                .build();
    }
}
