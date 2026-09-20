package gnoolson.saturday.app.repository.client.entity;

import gnoolson.saturday.app.repository.project.entity.ProjectEntity;
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
@Entity(name = "Client")
@Table(name = "s_client")
public class ClientEntity implements Serializable {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "description", nullable = false, length = 1024)
    private String description;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "server_url", nullable = false, length = 128)
    private String serverURI;

    @Column(name = "use_auth", nullable = false)
    private boolean useAuth;

    @Column(name = "auth_username", nullable = false, length = 128)
    private String authUsername;

    @Column(name = "auth_password", nullable = false, length = 128)
    private String authPassword;

    @Column(name = "connected_at", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private long connectedAt;

    @Column(name = "disconnected_at", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private long disconnectedAt;

    @Lob
    @Column(name = "connection_error", nullable = false)
    private String connectionError;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE, mappedBy = "client")
    private List<SubscriptionEntity> subscriptions = new ArrayList<>(0);

    @Type(type = "uuid-char")
    @Column(name = "connected_event_handler", nullable = false, columnDefinition = "VARCHAR(36) DEFAULT '00000000-0000-0000-0000-000000000000'")
    private UUID connectedEventHandler;

    @Type(type = "uuid-char")
    @Column(name = "disconnected_event_handler", nullable = false, columnDefinition = "VARCHAR(36) DEFAULT '00000000-0000-0000-0000-000000000000'")
    private UUID disconnectedEventHandler;

    /*
     *
     *
     * */
    public ClientEntity(UUID id) {
        this.id = id;
    }

}
