package gnoolson.saturday.app.repository.project.entity;

import gnoolson.saturday.app.repository.client.entity.ClientEntity;
import gnoolson.saturday.app.repository.dashboard.entity.DashboardEntity;
import gnoolson.saturday.app.repository.schedule.entity.ScheduleEntity;
import gnoolson.saturday.app.repository.script.entity.ScriptEntity;
import gnoolson.saturday.app.repository.subscription.entity.SubscriptionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Project")
@Table(name = "s_project")
public class ProjectEntity implements Serializable {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 32, unique = true)
    private String name;

    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "clob default ''")
    private String description;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "project")
    private List<ScriptEntity> scripts = new ArrayList<>(0);

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "project")
    private List<DashboardEntity> dashboards = new ArrayList<>(0);

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "project")
    private List<ClientEntity> clients = new ArrayList<>(0);

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "project")
    private List<ScheduleEntity> schedules = new ArrayList<>(0);

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "project")
    private List<SubscriptionEntity> subscriptions = new ArrayList<>(0);

    /*
     *
     *
     * */
    public ProjectEntity(UUID id) {
        this.id = id;
    }

}
