package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.util.List;

public class AccountNameFilter implements IFilter<DestinationAccount> {
    private final String searchText;

    public AccountNameFilter(String bankName) {
        this.searchText = bankName;
    }

    @Override
    public List<DestinationAccount> apply(List<DestinationAccount> list) {
        return list.stream()
                .filter(account -> (account.getBankName() != null && account.getBankName().toLowerCase().contains(searchText.toLowerCase()))
                        || (account.getClientName() != null && account.getClientName().toLowerCase().contains(searchText.toLowerCase()))
                        || (account.getAccountAliases() != null && account.getAccountAliases().toLowerCase().contains(searchText.toLowerCase())))
                .toList();
    }
}
