package ru.otus.order_processing.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.otus.order_processing.model.OrderStatus;

import java.io.IOException;

public class StatusSerializer extends StdSerializer<OrderStatus> {

    static final String FIELD_NAME = "name";

    protected StatusSerializer() {
        super(OrderStatus.class);
    }

    @Override
    public void serialize(OrderStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName(StatusSerializer.FIELD_NAME);
        gen.writeString(value.name());
        gen.writeEndObject();
    }
}
