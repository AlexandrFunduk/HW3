package ru.liga.prerevolutionary.tinderserver.dto.converter;

public enum Sex {
    MALE("Сударь"),
    FEMALE("Сударыня"),
    ALL("Все");
    private final String title;

    Sex(String title) {
        this.title = title;
    }

    public static Sex ofTitle(String title) {
        return switch (title) {
            case "Сударь" -> MALE;
            case "Сударыня" -> FEMALE;
            case "Все" -> ALL;
            default -> throw new IllegalArgumentException("Not support title " + title);
        };
    }

    String getTitle() {
        return title;
    }
}
