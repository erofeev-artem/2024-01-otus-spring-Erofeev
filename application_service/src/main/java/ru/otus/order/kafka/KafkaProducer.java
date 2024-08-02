package ru.otus.order.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import ru.otus.order.dto.OrderDto;

import java.util.function.Consumer;


public class KafkaProducer implements DataProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    private final String topic;

    private final KafkaTemplate<String, OrderDto> template;

    private final Consumer<OrderDto> sendAsk;

    public KafkaProducer(
            String topic,
            KafkaTemplate<String, OrderDto> template,
            Consumer<OrderDto> sendAsk) {
        this.topic = topic;
        this.template = template;
        this.sendAsk = sendAsk;
    }

    @Override
    public void send(OrderDto orderDto) {
        try {
            LOG.info("value:{}", orderDto);
            template.send(topic, orderDto)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    LOG.info(
                                            "message:was sent, offset:{}",
                                            result.getRecordMetadata().offset());
                                    sendAsk.accept(orderDto);
                                } else {
                                    LOG.error("message :{} was not sent", orderDto, ex);
                                }
                            });
        } catch (Exception ex) {
            LOG.error("send error, value:{}", orderDto, ex);
        }
    }
}
