package ru.otus.order.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import ru.otus.order.dto.OrderDto;

import java.util.function.Consumer;


public class KafkaProducer implements DataProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    private final String topic;

    private final KafkaTemplate<String, OrderMessage> template;

    private final Consumer<OrderMessage> sendAsk;

    public KafkaProducer(
            String topic,
            KafkaTemplate<String, OrderMessage> template,
            Consumer<OrderMessage> sendAsk) {
        this.topic = topic;
        this.template = template;
        this.sendAsk = sendAsk;
    }

    @Override
    public void send(OrderMessage orderMessage) {
        try {
            LOG.info("value:{}", orderMessage);
            template.send(topic, orderMessage)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    LOG.info(
                                            "message:was sent, offset:{}",
                                            result.getRecordMetadata().offset());
                                    sendAsk.accept(orderMessage);
                                } else {
                                    LOG.error("message :{} was not sent", orderMessage, ex);
                                }
                            });
        } catch (Exception ex) {
            LOG.error("send error, value:{}", orderMessage, ex);
        }
    }
}
