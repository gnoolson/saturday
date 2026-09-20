package gnoolson.saturday.app.web.dto;

import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class ValidationResultDto {
    private final String field;
    private final String message;

    public ValidationResultDto(FieldError fieldError) {
        this.field = fieldError.getField();
        this.message = fieldError.getDefaultMessage();
    }

    public ValidationResultDto(String field, String message) {
        this.field = field;
        this.message = message;
    }

}