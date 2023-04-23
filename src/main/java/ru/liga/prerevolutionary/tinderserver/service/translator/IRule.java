package ru.liga.prerevolutionary.tinderserver.service.translator;

import java.util.List;
import java.util.stream.Collectors;

public class IRule implements TranslateRule {
    @Override
    public List<String> apply(List<String> source) {
        return source.stream().map(this::translate).collect(Collectors.toList());
    }

    private String translate(String str) {
        StringBuilder result = new StringBuilder(str);
        replaceIfAfterFromVowel(str, result, 'и', 'i');
        replaceIfAfterFromVowel(str, result, 'И', 'I');
        return result.toString();
    }

    private static void replaceIfAfterFromVowel(String str, StringBuilder result, char from, char to) {
        int index = str.indexOf(from);
        while (index != -1 && index < str.length() - 2) {
            if (isVowel(str.charAt(index + 1))) {

                result.setCharAt(index, to);
            }
            index = str.indexOf(from, index + 1);
        }
    }

    private static boolean isVowel(char ch) {
        // проверяем, является ли буква гласной
        return "аеёиоуыэюяй".indexOf(Character.toLowerCase(ch)) != -1;
    }
}
