package bg.com.bo.bff.commons.filters.interfaces;

import java.util.List;

public interface IFilter<T> {
    List<T> apply(List<T> list);
}
