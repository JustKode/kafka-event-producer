package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
public class StringMeta extends Meta {
    private String name;
    private Boolean isManual;
    private List<String> manualStrings;
    private Integer minLength;
    private Integer maxLength;

    public Boolean isValid() {
        if (!isManual) {
            return minLength <= maxLength && minLength >= 0;
        } else {
            return manualStrings != null;
        }
    }

    public String getRandomValue() {
        return null;
    }

    public static StringMeta getMetaByMap(Map<String, Object> map) {
        return null;
    }
}
