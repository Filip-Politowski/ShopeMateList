package pl.shopmatelist.shopmatelist.rest.exceptionHandlers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.shopmatelist.shopmatelist.exceptions.ProductOnListNotFoundException;
import pl.shopmatelist.shopmatelist.entity.response.ErrorResponse;

@ControllerAdvice
public class ProductOnListExceptionHandler {
    @ExceptionHandler(ProductOnListNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ProductOnListNotFoundException exc){
        ErrorResponse error = new ErrorResponse();
        error.setStatus(404);
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(error.getStatus()));
    }
}
