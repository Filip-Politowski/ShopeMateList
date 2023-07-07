package pl.shopmatelist.shopmatelist.rest.exceptionHandlers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.shopmatelist.shopmatelist.exceptions.IngredientNofFoundException;
import pl.shopmatelist.shopmatelist.rest.errorResponse.ErrorResponse;

@ControllerAdvice
public class IngredientExceptionHandler {
    @ExceptionHandler(IngredientNofFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(IngredientNofFoundException exc) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(404);
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(error.getStatus()));
    }
}
