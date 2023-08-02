package justkode.kafka.event.producer.meta;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FloatMetaTest {

    @Test
    void validCheck() {
        assertDoesNotThrow(() -> FloatMeta.builder().isManual(false).minValue(1.0f).maxValue(3.0f).build().validCheck(""));
        assertDoesNotThrow(() -> FloatMeta.builder().isManual(false).minValue(1.0f).maxValue(1.0f).build().validCheck(""));
        assertDoesNotThrow(() -> FloatMeta.builder().isManual(true).manualValues(Arrays.asList(1.1f, 1.2f, 1.3f, 1.4f)).build().validCheck(""));

        assertThrows(RuntimeException.class, () -> FloatMeta.builder().isManual(false).minValue(1.5f).maxValue(0.5f).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> FloatMeta.builder().isManual(false).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> FloatMeta.builder().isManual(true).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> FloatMeta.builder().isManual(true).manualValues(Collections.emptyList()).build().validCheck(""));
    }

    @Provide
    Arbitrary<Float> randomFloatMinus100to100() {
        return Arbitraries.floats().between(-100.0f, 100.0f);
    }

    @Property
    void getRandomValueInNonManual(
            @ForAll("randomFloatMinus100to100") Float value1,
            @ForAll("randomFloatMinus100to100") Float value2
    ) {
        Float minValue = value1 > value2 ? value2 : value1;
        Float maxValue = value1 > value2 ? value1 : value2;

        // non-manual random
        FloatMeta nonManualMeta = FloatMeta.builder().isManual(false).minValue(minValue).maxValue(maxValue).build();
        assertThat(nonManualMeta.getRandomValue()).isBetween(minValue, maxValue);
    }

    @Property
    void getRandomValueInManual(@ForAll List<@From("randomFloatMinus100to100") Float> listOfFloats) {
        // if listOfIntegers size == 0, it must be occurring error in ValidCheck method.
        if (listOfFloats.size() == 0)
            return;

        // manual random
        FloatMeta manualMeta = FloatMeta.builder().isManual(true).manualValues(listOfFloats).build();
        assertThat(manualMeta.getRandomValue()).isIn(listOfFloats);
    }
}