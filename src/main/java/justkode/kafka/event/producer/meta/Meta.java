package justkode.kafka.event.producer.meta;

import java.util.Map;

public abstract class Meta {
    public abstract Boolean isValid();
    public abstract Object getRandomValue();

    public static Meta getMetaByMap(Map<String, Object> map) {
        return null;
    }
}
