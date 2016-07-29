package hw.configs;

import hw.activemqtest.consumers.Consumer;
import hw.activemqtest.producers.Producer;
import hw.activemqtest.utils.Names;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;

/**
 * Created by zotova on 15.07.2016.
 */
@Configuration
public class WebsphereMQQueueConfiguration {

    @Autowired
    @Qualifier("ibmConnectionFactory")
    ConnectionFactory factory;

    @Bean
    @Qualifier("ibmInputConsumer")
    Consumer ibmInputConsumer (@Qualifier("ibmSimpleQueueTemplate")
                               JmsMessagingTemplate template) {
        return new Consumer("ibmConsumer", template);
    }

    @Bean
    @Qualifier("ibmOutputConsumer")
    Consumer ibmOutputConsumer (@Qualifier("ibmSimpleQueueTemplate")
                                        JmsMessagingTemplate template) {
        return new Consumer("ibmOutputConsumer", template);
    }


    @Bean
    @Qualifier("ibmInputListener")
    DefaultMessageListenerContainer ibmInputListener(
            @Qualifier("ibmInputConsumer") Consumer consumer
    ) {

        DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
        listener.setConnectionFactory(factory);
        listener.setDestinationName(Names.IBMInput);
        listener.setSessionTransacted(true);
        listener.setMessageListener(consumer);
        listener.start();
        return listener;
    }

    @Bean
    @Qualifier("ibmOutputListener")
    DefaultMessageListenerContainer ibmOutputListener(@Qualifier("ibmOutputConsumer")
                                                      Consumer consumer) {

        DefaultMessageListenerContainer listener = new DefaultMessageListenerContainer();
        listener.setConnectionFactory(factory);
        listener.setDestinationName(Names.IBMOutput);
        listener.setSessionTransacted(true);
        listener.setMessageListener(consumer);
        listener.start();
        return listener;
    }




    @Bean
    Producer inputProducer(@Qualifier("ibmSimpleQueueTemplate")
                                   JmsMessagingTemplate template) {
        return new Producer(Names.IBMInput, template);
    }

    @Bean
    Producer outputProducer(@Qualifier("ibmSimpleQueueTemplate")
                                    JmsMessagingTemplate template) {
        return new Producer(Names.IBMOutput, template);
    }

}
