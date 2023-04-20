package ru.liga.prerevolutionary.tinderserver.exception;

public class NotAllowRequest extends RuntimeException {
    public NotAllowRequest(String message) {
        super(message);
    }
}
