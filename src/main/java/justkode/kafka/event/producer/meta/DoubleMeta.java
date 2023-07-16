package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class DoubleMeta {
    private String name;
    private Boolean isManual;
    private List<Integer> manualIntegers;
    private Double minValue;
    private Double maxValue;

    public Boolean isValid() {
        if (!isManual) {
            return minValue <= maxValue;
        } else {
            return manualIntegers != null;
        }
    }
}
