package bg.com.bo.bff.mappings.providers.dpf;

import bg.com.bo.bff.application.dtos.response.dpf.DpfDataResponse;
import bg.com.bo.bff.application.dtos.response.dpf.DpfListResponse;
import bg.com.bo.bff.providers.dtos.response.dpf.mw.DpfMWResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DPFMapper implements IDPFMapper {
    public DpfListResponse mapToDPFListResponse(DpfMWResponse mwResponse) {
        List<DpfDataResponse> dataList = new ArrayList<>();
        if (mwResponse != null && mwResponse.getData() != null) {
            for(DpfMWResponse.DPFManagerMWData dpfManagerArray : mwResponse.getData()) {
                DpfDataResponse data = DpfDataResponse.builder()
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
        return DpfListResponse.builder()
                .data(dataList)
                .build();
    }
}
