package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

@Builder
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
        } else if (isManual && ((manualValues == null) || (manualValues.size() == 0))) {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }
    @Override
    public Float getRandomValue() {
        if (isManual) {
            return manualValues.get(random.nextInt(manualValues.size()));
        } else {
            if (maxValue.equals(minValue))
                return minValue;
            return abs(random.nextFloat() % (maxValue - minValue)) + minValue;
        }
    }

    public static FloatMeta getMetaByMap(String key, Map<String, Object> map) {
        Boolean isManual = (Boolean) map.get("is_manual");
        List<Double> inputManualValues = (List<Double>) map.get("manual_values");
        Double inputMinValue = (Double) map.get("min_value");
        Double inputMaxValue = (Double) map.get("max_value");
        FloatMeta floatMeta;

        if (isManual) {
            if (inputManualValues == null) {
                throw new RuntimeException("manual_values of " + key + " doesn't exists.");
            }

            floatMeta = FloatMeta.builder()
                    .isManual(true)
                    .manualValues(inputManualValues.stream().map(Double::floatValue).collect(Collectors.toList()))
                    .build();
        } else {
            if (inputMinValue == null) {
                throw new RuntimeException("min_value of " + key + " doesn't exists.");
            }
            else if (inputMaxValue == null) {
                throw new RuntimeException("min_value of " + key + " doesn't exists.");
            }

            floatMeta = FloatMeta.builder()
                    .isManual(false)
                    .minValue(inputMinValue.floatValue())
                    .maxValue(inputMaxValue.floatValue())
                    .build();
        }

        floatMeta.validCheck(key);
        return floatMeta;
    }
}
