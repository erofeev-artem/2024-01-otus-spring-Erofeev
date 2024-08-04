package ru.otus.order_processing.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import ru.otus.order_processing.kafka.OrderConsumer;
import ru.otus.order_processing.kafka.OrderConsumerService;
import ru.otus.order_processing.kafka.OrderMessage;
import ru.otus.order_processing.mapper.OrderMapper;
import ru.otus.order_processing.service.OrderService;
import ru.otus.order_processing.service.TariffService;

import java.util.List;

@Configuration
public class KafkaConfig {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConfig.class);

    public final String topicName;

    public KafkaConfig(@Value("${application.kafka.topic}") String topicName) {
        this.topicName = topicName;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ConsumerFactory<String, OrderMessage> consumerFactory(
            KafkaProperties kafkaProperties, ObjectMapper mapper) {

        JsonDeserializer<OrderMessage> deserializer = new JsonDeserializer<>(OrderMessage.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        var props = kafkaProperties.buildConsumerProperties();
//        var props = new HashMap<String, Object>();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
//        props.put(TYPE_MAPPINGS, "ru.otus.order_processing.dto.OrderMessage.java:ru.otus.order_processing.dto.OrderMessage.java");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3_000);

//        var deserializer = new JsonDeserializer<OrderMessage>();
//        deserializer.addTrustedPackages("ru.otus.order_processing.dto.OrderMessage");

        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, OrderMessage>(props);
        kafkaConsumerFactory.setValueDeserializer(deserializer);
        return kafkaConsumerFactory;
    }

    @Bean("listenerContainerFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderMessage>>
    listenerContainerFactory(ConsumerFactory<String, OrderMessage> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderMessage>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setIdleBetweenPolls(1_000);
        factory.getContainerProperties().setPollTimeout(1_000);

        var executor = new SimpleAsyncTaskExecutor("k-consumer-");
        executor.setConcurrencyLimit(10);
        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);
        return factory;
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
    }

    @Bean
    public OrderConsumer orderConsumer(OrderService orderService, TariffService tariffService, OrderMapper orderMapper) {
        return new OrderConsumerService(orderService, tariffService, orderMapper);
    }

    @Bean
    public KafkaClient stringValueConsumer(OrderConsumer orderConsumer) {
        return new KafkaClient(orderConsumer);
    }

    public static class KafkaClient {
        private final OrderConsumer orderConsumer;

        public KafkaClient(OrderConsumer orderConsumer) {
            this.orderConsumer = orderConsumer;
        }

        @KafkaListener(
                topics = "${application.kafka.topic}",
                containerFactory = "listenerContainerFactory")
        public void listen(@Payload List<OrderMessage> values) {
            LOG.info("values, values.size:{}", values.size());
            orderConsumer.accept(values);
        }
    }
}
