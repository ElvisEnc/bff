package bg.com.bo.bff.application.dtos.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {
    private ContactDetail.SocialNetworks socialNetworks;
    private ContactDetail.AttentionLines attentionLines;
    private ContactDetail.Contact contact;

    public static class builder {
    }
}
