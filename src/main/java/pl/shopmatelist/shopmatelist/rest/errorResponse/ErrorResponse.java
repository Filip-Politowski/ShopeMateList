package pl.shopmatelist.shopmatelist.rest.errorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
