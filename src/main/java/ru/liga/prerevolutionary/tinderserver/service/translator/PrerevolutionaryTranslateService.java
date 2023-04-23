package ru.liga.prerevolutionary.tinderserver.service.translator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.prerevolutionary.tinderserver.dto.TinderUserDto;
import ru.liga.prerevolutionary.tinderserver.dto.converter.Converter;

import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class PrerevolutionaryTranslateService {
    private final Converter<TinderUserDto, List<String>> tinderUserDtoStringsConverter;
    private final Converter<List<String>, TinderUserDto> stringsTinderUserDtoConverter;
    private final List<TranslateRule> rules = List.of(new IRule(), new FitaRule(), new YatRule(), new SolidMarkRule());

    public List<String> translate(List<String> source) {
        return rules.stream()
                .map(translateRule -> (Function<List<String>, List<String>>) translateRule)
                .reduce(Function.identity(), Function::andThen)
                .apply(source);
    }

    public TinderUserDto translate(TinderUserDto dto) {
        List<String> dtoAsStrings = tinderUserDtoStringsConverter.convert(dto);
        List<String> translatedStrings = translate(dtoAsStrings);
        return stringsTinderUserDtoConverter.convert(translatedStrings, dto);
    }

    public String translate(String string) {
        return translate(List.of(string)).get(0);
    }
}
