package hw.configs;

import hw.activemqtest.consumers.Consumer;
import hw.activemqtest.producers.Producer;
import hw.activemqtest.utils.Names;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Created by zotova on 15.07.2016.
 */
@Configuration
public class WebsphereMQTopicConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    @Qualifier("topicConnectionFactory")
    ConnectionFactory factory;

    @Bean
    Consumer ibmDurableConsumer(@Qualifier("ibmSimpleQueueTemplate")
                                       JmsMessagingTemplate template) {
        return new Consumer("ibmDurableConsumer", template);
    }
    @Bean
    Consumer ibmSimpleConsumer(@Qualifier("ibmSimpleQueueTemplate")
                                       JmsMessagingTemplate template) {
        return new Consumer("ibmSimpleConsumer", template);
    }

    @Bean
    @Qualifier("ibmDurableConsumerListener")
    DefaultMessageListenerContainer ibmDurableConsumerListener(Consumer ibmDurableConsumer)
    {
        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setConnectionFactory(factory);
      //  dmlc.setClientId(Names.TopicManagerClientId);
        dmlc.setPubSubDomain(true);
        dmlc.setDestinationName(Names.IBMTopicName);
        dmlc.setMessageListener(ibmDurableConsumer);
        dmlc.setSessionTransacted(true);
        dmlc.setDurableSubscriptionName("durable sub");
        dmlc.setSubscriptionDurable(true);
        dmlc.start();
        return dmlc;
    }


    @Bean
    @Qualifier("ibmSimpleConsumerListener")
    DefaultMessageListenerContainer ibmSimpleConsumerListener (Consumer ibmSimpleConsumer) throws JMSException
    {
        DefaultMessageListenerContainer dmlc = new DefaultMessageListenerContainer();
        dmlc.setConnectionFactory(factory);
      //  dmlc.setClientId("ibmTopicId1");
       dmlc.setPubSubDomain(true);
        dmlc.setDestinationName(Names.IBMTopicName);
       // dmlc.setDestinationName(Names.IBMTopicName);
        dmlc.setMessageListener(ibmSimpleConsumer);
        dmlc.setSessionTransacted(true);
       // dmlc.setDurableSubscriptionName("durable sub");
        dmlc.setSubscriptionDurable(false);
        dmlc.start();
        return dmlc;
    }

    @Bean
    @Qualifier("ibmTopicProducer")
    Producer ibmTopicProducer(@Qualifier("ibmSimpleQueueTemplate") JmsMessagingTemplate template) {
        return new Producer(Names.IBMTopicName, template);
    }

}
