package hw.activemqtest.controllers;

import hw.activemqtest.producers.Producer;
import hw.activemqtest.utils.Names;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jms.Message;

/**
 * Created by zotova on 15.07.2016.
 */
@Controller
@RequestMapping("/webapp")
public class HelloController {


    @Autowired
    @Qualifier("ibmTopicProducer")
    Producer ibmTopicProducer;

    @Autowired
    @Qualifier("inputProducer")
    Producer inputProducer;

    @Autowired
    @Qualifier("outputProducer")
    Producer outputProducer;

    @RequestMapping("/inputQueueTest")
    String inputQueueTest() {

        hw.activemqtest.domain.Message m = new  hw.activemqtest.domain.Message();

        m.setDestinationQueueName(Names.IBMInput);
        m.setMessageText("HW! Its a VALID message");
        m.setMessageId(1);

        inputProducer.sendMessage(m);

        return "index";
    }

    @RequestMapping("/outputQueueTest")
    String outputQueueTest() {

        hw.activemqtest.domain.Message m = new  hw.activemqtest.domain.Message();

        m.setDestinationQueueName(Names.IBMInput);
        m.setMessageText("HW! Its a VALID message");
        m.setMessageId(1);

        outputProducer.sendMessage(m);

        return "index";
    }

    @RequestMapping("/inputQueueInvalidTest")
    String inputQueueInvalidTest() {

        inputProducer.sendMessage("HEY its INVALID message");

        return "index";
    }


    @RequestMapping("/topicTest")
    String topicTest() {
        hw.activemqtest.domain.Message m = new  hw.activemqtest.domain.Message();

        m.setDestinationQueueName(Names.IBMInput);
        m.setMessageText("HW! Its a VALID message");
        m.setMessageId(1);

        ibmTopicProducer.sendMessage(m);
        return "index";
    }

    @RequestMapping("/topicInvalidTest")
    String topicInvalidTest() {

        ibmTopicProducer.sendMessage("HEY Its an INVALID message");
        return "index";
    }

}