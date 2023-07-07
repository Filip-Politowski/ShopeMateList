package pl.shopmatelist.shopmatelist.rest.exceptionHandlers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.shopmatelist.shopmatelist.exceptions.FoodPlanNotFoundException;
import pl.shopmatelist.shopmatelist.dto.response.ErrorResponse;

@ControllerAdvice
public class FoodPlanExceptionHandler {
    @ExceptionHandler(FoodPlanNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(FoodPlanNotFoundException exc) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(404);
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(error.getStatus()));
    }
}
