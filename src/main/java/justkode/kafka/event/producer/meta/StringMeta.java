package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class StringMeta implements Meta {
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
}
