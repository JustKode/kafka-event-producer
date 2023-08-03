package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class IntegerMeta extends Meta {
    private Boolean isManual;
    private List<Integer> manualValues;
    private Integer minValue;
    private Integer maxValue;

    @Override
    public void validCheck(String key) {
        String baseMessage = String.format("meta data of '%s' is not valid: ", key);
        if (!isManual && minValue > maxValue) {
            throw new RuntimeException(baseMessage + "min_value must be lower or equal than max_value.");
        } else if (isManual && ((manualValues == null) || (manualValues.size() == 0))) {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }

    @Override
    public Integer getRandomValue() {
        if (isManual) {
            return manualValues.get(random.nextInt(manualValues.size()));
        } else {
            if (maxValue == minValue)
                return minValue;
            return random.nextInt(maxValue - minValue) + minValue;
        }
    }

    public static IntegerMeta getMetaByMap(String key, Map<String, Object> map) {
        Boolean isManual = (Boolean) map.get("is_manual");
        List<Integer> manualValues = (List<Integer>) map.get("manual_values");
        Integer minValue = (Integer) map.get("min_value");
        Integer maxValue = (Integer) map.get("max_value");

        if (isManual && manualValues == null) {
            throw new RuntimeException("manual_values doesn't exists.");
        }
        IntegerMeta integerMeta = new IntegerMeta(isManual, manualValues, minValue, maxValue);
        integerMeta.validCheck(key);

        return integerMeta;
    }
}
