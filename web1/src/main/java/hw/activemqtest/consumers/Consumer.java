package hw.activemqtest.consumers;

import hw.activemqtest.producers.Producer;
import hw.activemqtest.services.XmlMarshaller;
import hw.activemqtest.services.XmlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.JMSException;
import javax.jms.MessageListener;

/**
 * Created by zotova on 06.07.2016.
 */

public class Consumer implements MessageListener{

    private String name;
    protected Producer nextProducer = null;

    public Consumer() {
    }

    public Consumer(String name, JmsMessagingTemplate template) {
        this.name = name;
        this.template = template;
    }

    JmsMessagingTemplate template;

    @Autowired
    XmlMarshaller<hw.activemqtest.domain.Message> marshaller;

    @Autowired
    XmlValidator validator;

    private String messageToString(javax.jms.Message message) throws Exception{
        return  (String)new SimpleMessageConverter().fromMessage(message);
    }

    private void printLog(String message) {
        System.out.println("------------------" + name
                + " consumer got message--------------------");
        System.out.println(message);
    }

    protected void handleMessage(javax.jms.Message message) throws Exception {

        String textMessage = messageToString(message);
        validator.validate(textMessage);
        printLog(textMessage);
        if (nextProducer != null)
            nextProducer.sendMessage(textMessage);
    }

     //JmsListener(destination = Names.AQueueName, containerFactory = "jmsContainerFactory")
    @Override
    public void onMessage(javax.jms.Message message) {
        try {

            handleMessage(message);
        }
        catch(Exception e) {
            throw JmsUtils.convertJmsAccessException(
                    new JMSException("Message in " + name
                            + " consumer is not valid! Abort transaction"));
        }
    }
}
