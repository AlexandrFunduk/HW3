package ru.liga.prerevolutionary.tinderserver.service.translator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FitaRule implements TranslateRule {
    private final static Map<String, String> fitaWord = new HashMap<>();

    //todo нельзя здесь просто сделать String.replace("ф", "ѳ"); ?
    static {
        fitaWord.put("Агафья", "Агаѳья");
        fitaWord.put("Анфимъ", "Анѳимъ");
        fitaWord.put("Анфим", "Анѳим");
        fitaWord.put("Афанасій", "Аѳанасій");
        fitaWord.put("Афанасий", "Аѳанасий");
        fitaWord.put("Афина", "Аѳина");
        fitaWord.put("Варфоломей", "Варѳоломей");
        fitaWord.put("Голіафъ", "Голіаѳъ");
        fitaWord.put("Голиафъ", "Голиаѳъ");
        fitaWord.put("Голіаф", "Голіаѳ");
        fitaWord.put("Голиаф", "Голиаѳ");
        fitaWord.put("Евфимій", "Евѳимій");
        fitaWord.put("Евфимий", "Евѳимий");
        fitaWord.put("Марфа", "Марѳа");
        fitaWord.put("Матфей", "Матѳей");
        fitaWord.put("Мефодій", "Меѳодій");
        fitaWord.put("Мефодий", "Меѳодий");
        fitaWord.put("Нафанаилъ", "Наѳанаилъ");
        fitaWord.put("Нафанаил", "Наѳанаил");
        fitaWord.put("Парфенонъ", "Парѳенонъ");
        fitaWord.put("Парфенон", "Парѳенон");
        fitaWord.put("Пифагоръ", "Пиѳагоръ");
        fitaWord.put("Пифагор", "Пиѳагор");
        fitaWord.put("Руфь", "Руѳь");
        fitaWord.put("Саваофъ", "Саваоѳъ");
        fitaWord.put("Саваоф", "Саваоѳ");
        fitaWord.put("Тимофей", "Тимоѳей");
        fitaWord.put("Эсфирь", "Эсѳирь");
        fitaWord.put("Іудифь", "Іудиѳь");
        fitaWord.put("Фаддей", "Ѳаддей");
        fitaWord.put("Фекла", "Ѳекла");
        fitaWord.put("Фемида", "Ѳемида");
        fitaWord.put("Фемистоклъ", "Ѳемистоклъ");
        fitaWord.put("Феодоръ", "Ѳеодоръ");
        fitaWord.put("Феодор", "Ѳеодор");
        fitaWord.put("Федя", "Ѳедя");
        fitaWord.put("Феодосій", "Ѳеодосій");
        fitaWord.put("Феодосий", "Ѳеодосий");
        fitaWord.put("Федосій", "Ѳедосій");
        fitaWord.put("Федосий", "Ѳедосий");
        fitaWord.put("Феодосія", "Ѳеодосія");
        fitaWord.put("Феодосия", "Ѳеодосия");
        fitaWord.put("Феодотъ", "Ѳеодотъ");
        fitaWord.put("Феодот", "Ѳеодот");
        fitaWord.put("Федотъ", "Ѳедотъ");
        fitaWord.put("Федот", "Ѳедот");
        fitaWord.put("Феофанъ", "Ѳеофанъ");
        fitaWord.put("Феофан", "Ѳеофан");
        fitaWord.put("Феофилъ", "Ѳеофилъ");
        fitaWord.put("Феофил", "Ѳеофил");
        fitaWord.put("Ферапонтъ", "Ѳерапонтъ");
        fitaWord.put("Ферапонт", "Ѳерапонт");
        fitaWord.put("Фома", "Ѳома");
        fitaWord.put("Фоминична", "Ѳоминична");
    }

    @Override
    public List<String> apply(List<String> source) {
        return source.stream().map(this::translate).collect(Collectors.toList());
    }

    private String translate(String str) {
        for (Map.Entry<String, String> entry : fitaWord.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            str = str.replaceAll(key, value);
        }
        return str;
    }
}
