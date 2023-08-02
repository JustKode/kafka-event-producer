package justkode.kafka.event.producer.meta;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DoubleMetaTest {

    @Test
    void validCheck() {
        assertDoesNotThrow(() -> DoubleMeta.builder().isManual(false).minValue(1.0).maxValue(3.0).build().validCheck(""));
        assertDoesNotThrow(() -> DoubleMeta.builder().isManual(false).minValue(1.0).maxValue(1.0).build().validCheck(""));
        assertDoesNotThrow(() -> DoubleMeta.builder().isManual(true).manualValues(Arrays.asList(1.0, 1.1, 1.2, 1.3)).build().validCheck(""));

        assertThrows(RuntimeException.class, () -> DoubleMeta.builder().isManual(false).minValue(1.5).maxValue(0.5).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> DoubleMeta.builder().isManual(false).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> DoubleMeta.builder().isManual(true).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> DoubleMeta.builder().isManual(true).manualValues(Collections.emptyList()).build().validCheck(""));
    }

    @Provide
    Arbitrary<Double> randomDoubleMinus100to100() {
        return Arbitraries.doubles().between(-100.0, 100.0);
    }

    @Property
    void getRandomValueInNonManual(
            @ForAll("randomDoubleMinus100to100") Double value1,
            @ForAll("randomDoubleMinus100to100") Double value2
    ) {
        Double minValue = value1 > value2 ? value2 : value1;
        Double maxValue = value1 > value2 ? value1 : value2;

        // non-manual random
        DoubleMeta nonManualMeta = DoubleMeta.builder().isManual(false).minValue(minValue).maxValue(maxValue).build();
        assertThat(nonManualMeta.getRandomValue()).isBetween(minValue, maxValue);
    }

    @Property
    void getRandomValueInManual(@ForAll List<@From("randomDoubleMinus100to100") Double> listOfDoubles) {
        // if listOfIntegers size == 0, it must be occurring error in ValidCheck method.
        if (listOfDoubles.size() == 0)
            return;

        // manual random
        DoubleMeta manualMeta = DoubleMeta.builder().isManual(true).manualValues(listOfDoubles).build();
        assertThat(manualMeta.getRandomValue()).isIn(listOfDoubles);
    }
}