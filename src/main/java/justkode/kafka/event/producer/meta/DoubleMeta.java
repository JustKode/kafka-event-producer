package justkode.kafka.event.producer.meta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter @Setter
@AllArgsConstructor
public class DoubleMeta extends Meta {
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

    @Override
    public Double getRandomValue() {
        return null;
    }

    public static DoubleMeta getMetaByMap(Map<String, Object> map) {
        return null;
    }
}
