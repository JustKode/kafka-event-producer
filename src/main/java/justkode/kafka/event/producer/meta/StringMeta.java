package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
public class StringMeta extends Meta {
    private Boolean isManual;
    private List<String> manualValues;
    private Integer minLength;
    private Integer maxLength;

    public void validCheck(String key) {
        String baseMessage = String.format("meta data of '%s' is not valid: ", key);
        if (!isManual && minLength > maxLength) {
            throw new RuntimeException(baseMessage + "min_length must be lower or equal than max_length.");
        } else if (isManual && ((manualValues != null) || (manualValues.size() == 0))) {
            throw new RuntimeException(baseMessage + "If is_manual is true, manual_values must be containing values.");
        }
    }

    public String getRandomValue() {
        int length = random.nextInt(maxLength - minLength) + minLength;
        byte[] array = new byte[length];
        random.nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    public static StringMeta getMetaByMap(String key, Map<String, Object> map) {
        Boolean isManual = (Boolean) map.get("is_manual");
        List<String> manualValues = (List<String>) map.get("manual_values");
        Integer minLength = (Integer) map.get("min_length");
        Integer maxLength = (Integer) map.get("max_length");

        if (isManual && manualValues != null) {
            throw new RuntimeException("manual_integers doesn't exists.");
        }
        StringMeta stringMeta = new StringMeta(isManual, manualValues, minLength, maxLength);
        stringMeta.validCheck(key);

        return stringMeta;
    }
}
