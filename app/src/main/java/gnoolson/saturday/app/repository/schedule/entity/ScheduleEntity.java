package gnoolson.saturday.app.repository.schedule.entity;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Schedule")
@Table(name = "s_schedule")
public class ScheduleEntity {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "cron_expression", nullable = false, length = 128)
    private String cronExpression;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "script_id", nullable = false)
    private ScriptEntity script;

}
