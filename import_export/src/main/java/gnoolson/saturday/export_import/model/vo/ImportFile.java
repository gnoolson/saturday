package gnoolson.saturday.export_import.model.vo;

import gnoolson.saturday.common.validator.ValueObjectValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class ImportFile {

    private final byte[] bytes;

    /*
     *
     *
     * */
    public ImportFile(byte[] bytes) {
        ValueObjectValidator.checkNotNull(bytes, "ImportFile");
        this.bytes = bytes;
    }

    public static ImportFile of(byte[] bytes) {
        return new ImportFile(bytes);
    }

}
