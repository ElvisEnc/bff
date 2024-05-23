package bg.com.bo.bff.application.dtos.response;

import bg.com.bo.bff.application.dtos.response.user.ContactDetail;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;

public class GetContactResponseFixture {
    public static ContactResponse withDefault(){
        return new ContactResponse(
                ContactDetail.SocialNetworks.builder()
                        .facebook("facebook")
                        .likedin("likedin")
                        .youtube("youtube")
                        .build(),
                ContactDetail.AttentionLines.builder()
                        .helpNumber("helpNumber")
                        .creditNumber("creditNumber")
                        .build(),
                ContactDetail.Contact.builder()
                        .whatsapp("whatsapp")
                        .countryNumberCode("countryNumberCode")
                        .build()
        );
    }
}
