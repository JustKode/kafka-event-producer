package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

@Builder
@Getter @Setter
@AllArgsConstructor
public class LongMeta extends Meta {
    private Boolean isManual;
    private List<Long> manualValues;
    private Long minValue;
    private Long maxValue;

    public void validCheck(String key) {
        String baseMessage = String.format("meta data of '%s' is not valid: ", key);
        if (!isManual && minValue > maxValue) {
            throw new RuntimeException(baseMessage + "min_value must be lower or equal than max_value.");
        } else if (isManual && ((manualValues == null) || (manualValues.size() == 0))) {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }

    @Override
    public Long getRandomValue() {
        if (isManual) {
            return manualValues.get(random.nextInt(manualValues.size()));
        } else {
            if (maxValue == minValue)
                return minValue;
            return abs(random.nextLong() % (maxValue - minValue)) + minValue;
        }
    }

    public static LongMeta getMetaByMap(String key, Map<String, Object> map) {
        Boolean isManual = (Boolean) map.get("is_manual");
        List<Long> manualValues = (List<Long>) map.get("manual_values");
        Long minValue = (Long) map.get("min_value");
        Long maxValue = (Long) map.get("max_value");

        if (isManual && manualValues != null) {
            throw new RuntimeException("manual_integers doesn't exists.");
        }
        LongMeta longMeta = new LongMeta(isManual, manualValues, minValue, maxValue);
        longMeta.validCheck(key);

        return longMeta;
    }
}

