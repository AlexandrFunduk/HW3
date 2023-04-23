package ru.liga.prerevolutionary.tinderserver.service.translator;

import java.util.List;
import java.util.stream.Collectors;

public class SolidMarkRule implements TranslateRule {
    @Override
    public List<String> apply(List<String> source) {
        return source.stream().map(this::translate).collect(Collectors.toList());
    }

    private String translate(String str) {
        StringBuilder result = new StringBuilder();
        String[] words = str.split("\\b");
        for (String word : words) {
            if (word.matches(".*[бвгджзклмнпрстфхцчшщ]$")) {
                result.append(word)
                        .append("ъ");
            } else {
                result.append(word);
            }
        }
        return result.toString();
    }


}
