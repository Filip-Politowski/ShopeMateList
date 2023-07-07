package pl.shopmatelist.shopmatelist.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteResponse {

    String message;
    int status;

}
