package ru.liga.prerevolutionary.tinderserver.exception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.liga.prerevolutionary.tinderserver.dto.ErrorDto;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> appException(Exception ex) {
        log.error("ApplicationException", ex);
        ErrorDto error = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(Exception ex) {
        log.warn("NotFoundException", ex);
        ErrorDto error = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedEntityException.class)
    public ResponseEntity<?> duplicatedEntityException(Exception ex) {
        log.warn("DuplicatedEntityException", ex);
        ErrorDto error = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotAllowRequest.class)
    public ResponseEntity<?> NotAllowRequestException(Exception ex) {
        log.warn("NotAllowRequest", ex);
        ErrorDto error = new ErrorDto(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
