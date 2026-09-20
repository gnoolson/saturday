package gnoolson.saturday.app.web.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HtmlDto {

    @NotNull
    private UUID id;

    @Length(max = 50000, message = "{dashboard.upsert.form.html.error}")
    private String html;

}
