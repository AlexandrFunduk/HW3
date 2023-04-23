package ru.liga.prerevolutionary.tinderserver.service.translator;

import java.util.List;
import java.util.function.Function;

public interface TranslateRule extends Function<List<String>, List<String>> {
}
