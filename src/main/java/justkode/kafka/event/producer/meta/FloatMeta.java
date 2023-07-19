package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
public class FloatMeta extends Meta {
    private Boolean isManual;
    private List<Float> manualValues;
    private Float minValue;
    private Float maxValue;

    public void validCheck(String key) {
        String baseMessage = String.format("meta data of '%s' is not valid: ", key);
        if (!isManual && minValue > maxValue) {
            throw new RuntimeException(baseMessage + "min_value must be lower or equal than max_value.");
        } else if (isManual && ((manualValues != null) || (manualValues.size() == 0))) {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }
    @Override
    public Float getRandomValue() {
        return random.nextFloat() % (maxValue - minValue) + minValue;
    }

    public static FloatMeta getMetaByMap(String key, Map<String, Object> map) {
        Boolean isManual = (Boolean) map.get("is_manual");
        List<Float> manualValues = (List<Float>) map.get("manual_values");
        Float minValue = (Float) map.get("min_value");
        Float maxValue = (Float) map.get("max_value");

        if (isManual && manualValues != null) {
            throw new RuntimeException("manual_integers doesn't exists.");
        }

        FloatMeta doubleMeta = new FloatMeta(isManual, manualValues, minValue, maxValue);
        doubleMeta.validCheck(key);

        return doubleMeta;
    }
}
