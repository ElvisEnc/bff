package bg.com.bo.bff.model.interfaces;

import bg.com.bo.bff.model.Account;
import bg.com.bo.bff.model.AccountListMWResponse;
import bg.com.bo.bff.model.AccountListResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-15T14:46:30-0400",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class AccountListMapperImpl implements AccountListMapper {

    @Override
    public AccountListResponse convert(AccountListMWResponse accountListMWResponse) {
        if ( accountListMWResponse == null ) {
            return null;
        }

        AccountListResponse accountListResponse = new AccountListResponse();

        List<Account> list = accountListMWResponse.getData();
        if ( list != null ) {
            accountListResponse.setData( new ArrayList<Account>( list ) );
        }

        return accountListResponse;
    }
}
