package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Builder
@Getter @Setter
@AllArgsConstructor
public class StringMeta extends Meta {
    private Boolean isManual;
    private List<String> manualValues;
    private Integer minLength;
    private Integer maxLength;

    public void validCheck(String key) {
        String baseMessage = String.format("meta data of '%s' is not valid: ", key);
        if (!isManual) {
            if (minLength > maxLength)
                throw new RuntimeException(baseMessage + "min_length must be lower or equal than max_length.");
            else if (minLength < 0) {
                throw new RuntimeException(baseMessage + "min_length muse be higher or equal than 0.");
            }
        } else if (manualValues == null || manualValues.size() == 0) {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }

    public String getRandomValue() {
        if (isManual)
            return manualValues.get(random.nextInt(manualValues.size()));
        else {
            int length;

            if (maxLength.equals(minLength))
                length = minLength;
            else
                length = random.nextInt(maxLength - minLength) + minLength;

            return random.ints(97, 122 + 1)
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }
    }

    public static StringMeta getMetaByMap(String key, Map<String, Object> map) {
        Boolean isManual = (Boolean) map.get("is_manual");
        List<String> manualValues = (List<String>) map.get("manual_values");
        Double minLength = (Double) map.get("min_length");
        Double maxLength = (Double) map.get("max_length");
        StringMeta stringMeta;

        if (isManual) {
            if (manualValues == null) {
                throw new RuntimeException("manual_integers doesn't exists.");
            }
            stringMeta = StringMeta.builder()
                            .isManual(true)
                            .manualValues(manualValues)
                            .build();
        } else {
            stringMeta = StringMeta.builder()
                            .isManual(false)
                            .minLength(minLength.intValue())
                            .maxLength(maxLength.intValue())
                            .build();
        }
        stringMeta.validCheck(key);

        return stringMeta;
    }
}
