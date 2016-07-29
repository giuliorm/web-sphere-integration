package hw.activemqtest.consumers;

import hw.activemqtest.producers.Producer;
import org.springframework.jms.core.JmsMessagingTemplate;

/**
 * Created by zotova on 08.07.2016.
 */
public class RedirectConsumer extends Consumer {


    public RedirectConsumer() {

    }

    public RedirectConsumer(String name, JmsMessagingTemplate template,
                            Producer next) {
        super(name, template);
        nextProducer = next;
    }
}
