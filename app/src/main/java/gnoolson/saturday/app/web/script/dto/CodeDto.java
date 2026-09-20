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

    @IncludedScriptsValidation(message = "{script.upsert.form.included_scripts.error}")
    @Size(min = 0, max = 32)
    private List<UUID> includedScripts;

    @NotNull
    @Length(max = 50000, message = "{script.upsert.form.code.error}")
    private String code;

}
