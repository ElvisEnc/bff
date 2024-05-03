package bg.com.bo.bff.providers.mappings.dpf;

import bg.com.bo.bff.application.dtos.response.DPFDataResponse;
import bg.com.bo.bff.application.dtos.response.DPFListResponse;
import bg.com.bo.bff.providers.dtos.responses.DPFMWResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DPFMapper implements IDPFMapper {
    public DPFListResponse mapToDPFListResponse(DPFMWResponse mwResponse) {
        List<DPFDataResponse> dataList = new ArrayList<>();
        if (mwResponse != null && mwResponse.getData() != null) {
            for(DPFMWResponse.DPFManagerMWData dpfManagerArray : mwResponse.getData()) {
                DPFDataResponse data = DPFDataResponse.builder()
                        .numDPF(dpfManagerArray.getNumDpf())
                        .numDpfBGA(dpfManagerArray.getNumDpfBGA())
                        .clientName(dpfManagerArray.getClientName())
                        .capital(dpfManagerArray.getCapital())
                        .interes(dpfManagerArray.getInterest())
                        .total(dpfManagerArray.getTotal())
                        .codeCurrency(dpfManagerArray.getCodeCurrency())
                        .dischargeDate(dpfManagerArray.getHighDate())
                        .expirationDate(dpfManagerArray.getExpirationDate())
                        .term(dpfManagerArray.getTerm())
                        .rate(dpfManagerArray.getRate())
                        .paymentFrequency(dpfManagerArray.getPaymentMethod())
                        .build();
                dataList.add(data);
            }
        }
        return DPFListResponse.builder()
                .data(dataList)
                .build();
    }
}
