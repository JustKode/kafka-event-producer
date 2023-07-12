package justkode.kafka.event.producer.meta;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class StringMeta {
    private String name;
    private Boolean isRandomString;
    private List<String> manualStrings;
    private Integer minLength;
    private Integer maxLength;

    public boolean isValid() {
        if (isRandomString) {
            return minLength <= maxLength && minLength >= 0;
        } else {
            return manualStrings != null;
        }
    }
}
