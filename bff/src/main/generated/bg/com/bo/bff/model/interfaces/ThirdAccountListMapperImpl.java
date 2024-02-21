package bg.com.bo.bff.model.interfaces;

import bg.com.bo.bff.model.ThirdAccount;
import bg.com.bo.bff.model.ThirdAccountListMWResponse;
import bg.com.bo.bff.model.ThirdAccountListResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-21T12:06:14-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class ThirdAccountListMapperImpl implements ThirdAccountListMapper {

    @Override
    public ThirdAccountListResponse convert(ThirdAccountListMWResponse accountListMWResponse) {
        if ( accountListMWResponse == null ) {
            return null;
        }

        ThirdAccountListResponse thirdAccountListResponse = new ThirdAccountListResponse();

        List<ThirdAccount> list = accountListMWResponse.getData();
        if ( list != null ) {
            thirdAccountListResponse.setData( new ArrayList<ThirdAccount>( list ) );
        }

        return thirdAccountListResponse;
    }
}
