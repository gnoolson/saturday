package gnoolson.saturday.app.web.import_export.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileNameDto {

    @NotBlank
    private String fileName;

}
