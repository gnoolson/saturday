package gnoolson.saturday.app.repository.script.entity;


import gnoolson.saturday.app.repository.dashboard.entity.DashboardEntity;
import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
import gnoolson.saturday.app.repository.schedule.entity.ScheduleEntity;
import gnoolson.saturday.app.repository.subscription.entity.SubscriptionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Script")
@Table(name = "s_script")
public class ScriptEntity implements Serializable {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
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
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "blocked")
    private boolean blocked;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "script")
    private List<ScriptErrorEntity> errors = new ArrayList<>(0);

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "script")
    private List<ScheduleEntity> schedules = new ArrayList<>(0);

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "script")
    private List<DashboardEntity> dashboards = new ArrayList<>(0);

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "script")
    private List<SubscriptionEntity> subscriptions = new ArrayList<>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "s_included_script", joinColumns = @JoinColumn(name = "script_id", columnDefinition = "VARCHAR(36)"))
    @Column(name = "included_script_id", columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private Set<UUID> includedScripts = new HashSet<>();

    @Column(name = "caching_time")
    private int cachingTime;

    @Column(name = "autostart", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean autostart;

    @Type(type = "uuid-char")
    @Column(name = "error_event_handler", nullable = false, columnDefinition = "VARCHAR(36) DEFAULT '00000000-0000-0000-0000-000000000000'")
    private UUID errorEventHandler;

    /*
     *
     *
     * */
    public ScriptEntity(UUID id) {
        this.id = id;
    }

}