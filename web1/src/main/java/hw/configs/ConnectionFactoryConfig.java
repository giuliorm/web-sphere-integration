package hw.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/*import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic; */

/**
 * Created by zotova on 06.07.2016.
 */
@Configuration
public class ConnectionFactoryConfig {
    private static final String CONNECTION_FACTORY_NAME = "jms/wasConnectionFactory";
  //  private static final String QUEUE_MANAGER_JNDI_NAME = "Factories/factory2";
    private static final String TOPIC_MANAGER_JNDI_NAME = "jms/topicConnectionFactory";


    @Bean
    @Qualifier("topicConnectionFactory")
    ConnectionFactory topicConnectionFactory() {
        ConnectionFactory connectionFactory = null;
        try {
            Context context = new InitialContext();
            connectionFactory = (ConnectionFactory) context.lookup(TOPIC_MANAGER_JNDI_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return connectionFactory;
    }

    @Bean
    @Qualifier("ibmConnectionFactory")
    ConnectionFactory jmsListenerContainerFactory() {
        ConnectionFactory connectionFactory = null;
        try {
            Context context = new InitialContext();
            connectionFactory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY_NAME);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return connectionFactory;
    }

    @Bean
    @Qualifier("ibmSimpleQueueTemplate")
    JmsMessagingTemplate ibmSimpleQueueTemplate(@Qualifier("ibmConnectionFactory")
                                                  ConnectionFactory factory) {
        JmsTemplate queueTemplate = new JmsTemplate(factory);
        queueTemplate.setSessionTransacted(true);
        //queueTemplate.setSessionAcknowledgeMode();
        queueTemplate.setPubSubDomain(true);
        JmsMessagingTemplate queueMessagingTemplate =
                new JmsMessagingTemplate(queueTemplate);
        return queueMessagingTemplate;
    }

}
