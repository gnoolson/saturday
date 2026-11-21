package gnoolson.saturday.subscription.model.entity;

import gnoolson.saturday.common.model.vo.*;
import gnoolson.saturday.common.validator.DomainModelValidator;
import lombok.Getter;

@Getter
public class Subscription {

    private final SubscriptionId id;
    private final ProjectId projectId;
    private final ClientId clientId;
    private final ScriptId scriptId;
    private TopicFilter topicFilter;
    private QoS qos;
    private Description description;

    /*
     *
     *
     * */
    public Subscription(SubscriptionId id, ProjectId projectId, ClientId clientId, ScriptId scriptId, TopicFilter topicFilter, QoS qos, Description description) {
        DomainModelValidator.checkNotNull(id, "SubscriptionId");
        DomainModelValidator.checkNotNull(projectId, "ProjectId");
        DomainModelValidator.checkNotNull(clientId, "ClientId");
        DomainModelValidator.checkNotNull(scriptId, "ScriptId");
        DomainModelValidator.checkNotNull(topicFilter, "TopicFilter");
        DomainModelValidator.checkNotNull(qos, "QoS");
        DomainModelValidator.checkNotNull(description, "Description");

        this.id = id;
        this.projectId = projectId;
        this.clientId = clientId;
        this.scriptId = scriptId;
        this.topicFilter = topicFilter;
        this.qos = qos;
        this.description = description;
    }

    public void update(TopicFilter topicFilter, QoS qos, Description description) {
        DomainModelValidator.checkNotNull(topicFilter, "TopicFilter");
        DomainModelValidator.checkNotNull(description, "Description");
        DomainModelValidator.checkNotNull(qos, "QoS");

        this.topicFilter = topicFilter;
        this.qos = qos;
        this.description = description;
    }

}
