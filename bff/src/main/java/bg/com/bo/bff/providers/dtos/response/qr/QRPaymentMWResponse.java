package bg.com.bo.bff.providers.dtos.response.qr;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QRPaymentMWResponse {
    private MWResponse data;

    public static class MWResponse {
        @Schema(description = "Identificador estado de la transferencia", example = "APPROVED")
        private String status;
        @Schema(description = "maeId id de la Mae", example = "121")
        private String maeId;
        @Schema(description = "nroTransaction Número de transacción", example = "21211")
        private String nroTransaction;
        @Schema(description = "receiptDetail comprobante", example = "")
        private ReceiptDetailQrPayment receiptDetail;

        public MWResponse(String status, String maeId, String nroTransaction,ReceiptDetailQrPayment receiptDetail) {
            this.status = status;
            this.maeId = maeId;
            this.nroTransaction = nroTransaction;
            this.receiptDetail = receiptDetail;
        }

        public MWResponse() {
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMaeId() {
            return maeId;
        }

        public void setMaeId(String maeId) {
            this.maeId = maeId;
        }

        public String getNroTransaction() {
            return nroTransaction;
        }

        public void setNroTransaction(String nroTransaction) {
            this.nroTransaction = nroTransaction;
        }

        public ReceiptDetailQrPayment getReceiptDetail() {
            return receiptDetail;
        }

        public void setReceiptDetail(ReceiptDetailQrPayment receiptDetail) {
            this.receiptDetail = receiptDetail;
        }
    }
}



