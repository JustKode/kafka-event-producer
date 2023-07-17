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
    private List<String> manualValues;
    private Integer minLength;
    private Integer maxLength;

    public void validCheck(String key) {

    }

    public String getRandomValue() {
        return null;
    }

    public static StringMeta getMetaByMap(String key, Map<String, Object> map) {
        return null;
    }
}
