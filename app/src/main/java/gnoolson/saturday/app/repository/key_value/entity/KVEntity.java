package gnoolson.saturday.app.repository.key_value.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "KV")
@Table(name = "s_key_value")
public class KVEntity implements Serializable {

    @Id
    @Column(name = "key", nullable = false)
    private String key;

    @Lob
    @Column(name = "value", nullable = false, columnDefinition = "clob default ''")
    private String value;

}
