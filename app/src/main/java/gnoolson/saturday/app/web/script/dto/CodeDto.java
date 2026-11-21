package gnoolson.saturday.app.web.script.dto;

import gnoolson.saturday.app.web.script.dto.validation.IncludedScriptsValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeDto {

    @NotNull
    private UUID id;

    @IncludedScriptsValidation(message = "{error.script.included_scripts}")
    @Size(min = 0, max = 64)
    private List<UUID> includedScripts;

    @NotNull
    @Length(max = 5000) //TODO was 50_000
    private String code;

}
