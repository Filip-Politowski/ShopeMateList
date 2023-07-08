package pl.shopmatelist.shopmatelist.rest.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.shopmatelist.shopmatelist.exceptions.WeeklyFoodPlanNotFoundException;
import pl.shopmatelist.shopmatelist.entity.response.ErrorResponse;

@ControllerAdvice
public class WeeklyFoodPlanExceptionHandler {
    @ExceptionHandler(WeeklyFoodPlanNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(WeeklyFoodPlanNotFoundException exc) {
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatusCode.valueOf(error.getStatus()));
    }
}
