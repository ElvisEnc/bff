package bg.com.bo.bff.mappings;

import bg.com.bo.bff.mappings.interfaces.IGenericsMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
@lombok.NoArgsConstructor
public class GenericsMapper implements IGenericsMapper {

    public <S, T> Map<String, T> convert(List<S> list, Function<T, String> func, Function<S, T> conv) {
        HashMap<String, T> map = new HashMap<>();
        for (S item : list) {
            T itemConverted = conv.apply(item);
            String sad = func.apply(itemConverted);
            map.put(sad, itemConverted);
        }
        return map;
    }
}
