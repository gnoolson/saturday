package gnoolson.saturday.app.repository.dashboard.entity;


import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Dashboard")
@Table(name = "s_dashboard")
public class DashboardEntity implements Serializable {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Lob
    @Column(name = "html", nullable = false)
    private String html;

    @ManyToOne
    @JoinColumn(name = "script_id", nullable = false)
    private ScriptEntity script;

    @Column(name = "access", nullable = false, columnDefinition = "VARCHAR(16) DEFAULT 'EDITOR'")
    private String access;

    /*
     *
     *
     * */
    public DashboardEntity(UUID id) {
        this.id = id;
    }

}
