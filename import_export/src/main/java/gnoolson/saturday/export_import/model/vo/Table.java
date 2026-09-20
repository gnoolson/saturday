package gnoolson.saturday.export_import.model.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter
@RequiredArgsConstructor
public class Table {

    private final TableName name;
    private final List<FieldName> fields;
    private final List<Row> rows;

}


