package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class IntegerMeta implements Meta{
    private String name;
    private Boolean isManual;
    private List<Integer> manualIntegers;
    private Integer minValue;
    private Integer maxValue;

    public Boolean isValid() {
        if (!isManual) {
            return minValue <= maxValue;
        } else {
            return manualIntegers != null;
        }
    }

    @Override
    public Integer getRandomValue() {
        return null;
    }
}
