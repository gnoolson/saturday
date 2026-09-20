package gnoolson.saturday.app.repository.subscription.entity;


import gnoolson.saturday.app.repository.client.entity.ClientEntity;
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
@Entity(name = "Subscription")
@Table(name = "s_subscription")
public class SubscriptionEntity {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "script_id", nullable = false)
    private ScriptEntity script;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "topic_filter", nullable = false)
    private String topicFilter;

    /*
     *
     *
     * */
    public SubscriptionEntity(UUID id) {
        this.id = id;
    }

}
