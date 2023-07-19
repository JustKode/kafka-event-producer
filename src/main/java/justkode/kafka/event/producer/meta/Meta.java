package justkode.kafka.event.producer.meta;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class Meta {
    public abstract void validCheck(String key);
    public abstract Object getRandomValue();
    public static Meta getMetaByMap(String key, Map<String, Object> map) {
        throw new RuntimeException("Subclass of Meta needs to be implements of getMetaByMap");
    }
    protected static Random random = new Random();
}
