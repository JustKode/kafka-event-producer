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
    private List<Long> manualValues;
    private Long minValue;
    private Long maxValue;

    public void validCheck(String key) {
        String baseMessage = String.format("meta data of '%s' is not valid: ", key);
        if (!isManual && minValue > maxValue) {
            throw new RuntimeException(baseMessage + "min_value must be lower or equal than max_value.");
        } else {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }

    @Override
    public Long getRandomValue() {
        return null;
    }

    public static LongMeta getMetaByMap(String key, Map<String, Object> map) {
        return null;
    }
}

