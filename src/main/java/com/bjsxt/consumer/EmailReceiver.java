package com.bjsxt.consumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lvyelanshan
 * @create 2019-12-05 15:23
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                //绑定队列
                value = @Queue(value = "${mq.config.queue.email}",autoDelete = "true"),
                //绑定交换器
                exchange = @Exchange(value = "${mq.config.exchange}",type = ExchangeTypes.DIRECT),
                //绑定路由键
                key = "${mq.config.queue.email.rounting.key}"
        )
)
public class EmailReceiver {
    /**
     * 接收消息的方法。采用消息队列监听机制
     */
    @RabbitHandler //表示哪个消息队列接收到消息了，由这个方法进行处理
    public void process(String msg){
        System.out.println("EmailInfo:"+msg);
    }
}
