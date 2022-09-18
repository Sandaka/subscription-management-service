package com.kingston.msc.rabbitmq;

import com.kingston.msc.model.CPTransactionTracker;
import com.kingston.msc.model.StudentTransactionTracker;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Sandaka Wijesinghe.
 * Date: 8/11/22
 */
@Service
public class StudentRabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${learngenix.rabbitmq.exchange}")
    private String exchange;

    @Value("${learngenix.rabbitmq.stu.subscription.routingkey}")
    private String routingkey;

    public void sendToStudentQueue(StudentTransactionTracker studentTransactionTracker) {
        rabbitTemplate.convertAndSend(exchange, routingkey, studentTransactionTracker);
        System.out.println("Send msg = " + studentTransactionTracker);

    }
}
