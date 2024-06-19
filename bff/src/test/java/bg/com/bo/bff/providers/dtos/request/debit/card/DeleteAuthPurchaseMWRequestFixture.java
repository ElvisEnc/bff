package bg.com.bo.bff.providers.dtos.request.debit.card;

public class DeleteAuthPurchaseMWRequestFixture {
    public static DeleteAuthPurchaseMWRequest withDefault() {
        return DeleteAuthPurchaseMWRequest.builder()
                .identifierTD("123")
                .personId("123")
                .action("123")
                .idPci("123")
                .build();
    }
}