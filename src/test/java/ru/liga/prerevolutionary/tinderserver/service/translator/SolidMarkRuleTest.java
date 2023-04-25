package ru.liga.prerevolutionary.tinderserver.service.translator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SolidMarkRuleTest {
    private final SolidMarkRule solidMarkRule = new SolidMarkRule();

    @Test
    void apply() {
        List<String> inputList = List.of("этот слон", "тест  ", "должен!", "работать", "правильно", "длинный");
        List<String> expectedList = List.of("этотъ слонъ", "тестъ  ", "долженъ!", "работать", "правильно", "длинный");

        assertThat(solidMarkRule.apply(inputList)).isEqualTo(expectedList);
    }
}