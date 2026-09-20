package gnoolson.saturday.app.repository.script.entity;

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
@Entity(name = "ScriptError")
@Table(name = "s_script_error")
public class ScriptErrorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "script_id", nullable = false)
    private ScriptEntity script;

    @Column(name = "time")
    private long time;

    @Lob
    @Column(name = "stack", nullable = false)
    private String stack;

    @Lob
    @Column(name = "reason_of_error", nullable = false)
    private String reasonOfError;


}
