package bg.com.bo.bff.mappings.interfaces;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IGenericsMapper {
    <S, T> Map<String, T> convert(List<S> list, Function<T, String> func, Function<S, T> conv);
}
