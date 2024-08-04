package ru.otus.order_processing.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.otus.order_processing.model.OrderStatus;

import java.io.IOException;

public class StatusDeserializer extends StdDeserializer<OrderStatus> {

    protected StatusDeserializer() {
        super(OrderStatus.class);
    }

    @Override
    public OrderStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String name = node.get(StatusSerializer.FIELD_NAME).asText();
        return OrderStatus.valueOf(name);
    }
}
