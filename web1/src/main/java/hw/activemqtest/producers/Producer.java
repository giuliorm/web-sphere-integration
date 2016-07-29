package hw.activemqtest.producers;


import hw.activemqtest.domain.Message;
import hw.activemqtest.services.XmlMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Created by zotova on 08.07.2016.
 */

public class Producer {

    @Autowired
    XmlMarshaller marshaller;

    JmsMessagingTemplate template;
    String queueName;

    public Producer() {

    }

    public Producer(String queueName) {
        this.queueName = queueName;
    }

    public Producer(String queueName, JmsMessagingTemplate template) {
        this(queueName);
        this.template = template;
    }

    private void send(org.springframework.messaging.Message<?> message) {
        template.send(queueName, message);
        System.out.println("-------------------" + queueName + " producer has sent message ------------------");
    }

    public void sendMessage(Message message) {

        org.springframework.messaging.Message<String> m =
                MessageBuilder.withPayload(marshaller.toXml(message)).build();

        send(m);
    }

    public void sendMessage(String message) {
        org.springframework.messaging.Message<String> m =
                MessageBuilder.withPayload(message).build();

        send(m);
    }

}
