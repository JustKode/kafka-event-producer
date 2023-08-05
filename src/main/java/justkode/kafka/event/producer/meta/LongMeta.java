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
        List<Double> inputManualValues = (List<Double>) map.get("manual_values");
        Double inputMinValue = (Double) map.get("min_value");
        Double inputMaxValue = (Double) map.get("max_value");
        LongMeta longMeta;

        if (isManual) {
            if (inputManualValues == null) {
                throw new RuntimeException("manual_values of " + key + " doesn't exists.");
            }

            longMeta = LongMeta.builder()
                    .isManual(true)
                    .manualValues(inputManualValues.stream().map(Double::longValue).collect(Collectors.toList()))
                    .build();
        } else {
            if (inputMinValue == null) {
                throw new RuntimeException("min_value of " + key + " doesn't exists.");
            }
            else if (inputMaxValue == null) {
                throw new RuntimeException("min_value of " + key + " doesn't exists.");
            }

            longMeta = LongMeta.builder()
                    .isManual(false)
                    .minValue(inputMinValue.longValue())
                    .maxValue(inputMaxValue.longValue())
                    .build();
        }

        longMeta.validCheck(key);
        return longMeta;
    }
}

