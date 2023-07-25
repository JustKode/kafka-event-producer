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
public class DoubleMeta extends Meta {
    private Boolean isManual;
    private List<Double> manualValues;
    private Double minValue;
    private Double maxValue;

    public void validCheck(String key) {
        String baseMessage = String.format("meta data of '%s' is not valid: ", key);
        if (!isManual && minValue > maxValue) {
            throw new RuntimeException(baseMessage + "min_value must be lower or equal than max_value.");
        } else if (isManual && ((manualValues == null) || (manualValues.size() == 0))) {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }

    @Override
    public Double getRandomValue() {
        if (isManual) {
            return manualValues.get(random.nextInt(manualValues.size()));
        } else {
            if (maxValue.equals(minValue))
                return minValue;
            return abs(random.nextDouble() % (maxValue - minValue)) + minValue;
        }
    }

    public static DoubleMeta getMetaByMap(String key, Map<String, Object> map) {
        Boolean isManual = (Boolean) map.get("is_manual");
        List<Double> manualValues = (List<Double>) map.get("manual_doubles");
        Double minValue = (Double) map.get("min_value");
        Double maxValue = (Double) map.get("max_value");

        if (isManual && manualValues != null) {
            throw new RuntimeException("manual_integers doesn't exists.");
        }

        DoubleMeta doubleMeta = new DoubleMeta(isManual, manualValues, minValue, maxValue);
        doubleMeta.validCheck(key);
        return doubleMeta;
    }
}
