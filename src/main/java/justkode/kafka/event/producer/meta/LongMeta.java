package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class LongMeta extends Meta {
    private String name;
    private Boolean isManual;
    private List<Long> manualIntegers;
    private Long minValue;
    private Long maxValue;

    public Boolean isValid() {
        if (!isManual) {
            return minValue <= maxValue;
        } else {
            return manualIntegers != null;
        }
    }

    @Override
    public Long getRandomValue() {
        return null;
    }

    public static LongMeta getMetaByMap(Map<String, Object> map) {
        return null;
    }
}

