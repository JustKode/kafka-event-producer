package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LongMeta implements Meta {
    private String name;
    private Boolean isManual;
    private List<Long> manualIntegers;
    private Long minValue;
    private Long maxValue;

    public Boolean isValid() {
        if (!isManual) {
            return minValue <= maxValue;
        } else {
            return manualIntegers != null;
        }
    }

    @Override
    public Long getRandomValue() {
        return null;
    }
}

