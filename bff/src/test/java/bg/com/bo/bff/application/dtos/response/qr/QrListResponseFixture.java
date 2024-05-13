package bg.com.bo.bff.application.dtos.response.qr;

import java.math.BigDecimal;
import java.util.Arrays;

public class QrListResponseFixture {
    public static QrListResponse withDefault() {
        return QrListResponse.builder()
                .generated(Arrays.asList(
                        QrGeneratedPaidFixture.withDefault()
                ))
                .totalGenerated(1)
                .paid(Arrays.asList(
                        QrGeneratedPaidFixture.withDefault()
                ))
                .totalPaid(1)
                .build();
    }
}