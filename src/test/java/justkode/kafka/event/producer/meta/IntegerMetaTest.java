package justkode.kafka.event.producer.meta;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class IntegerMetaTest {

    @Test
    void validCheck() {
        assertDoesNotThrow(() -> IntegerMeta.builder().isManual(false).minValue(1).maxValue(3).build().validCheck(""));
        assertDoesNotThrow(() -> IntegerMeta.builder().isManual(false).minValue(1).maxValue(1).build().validCheck(""));
        assertDoesNotThrow(() -> IntegerMeta.builder().isManual(true).manualValues(List.of(1,2,3,4)).build().validCheck(""));

        assertThrows(RuntimeException.class, () -> IntegerMeta.builder().isManual(false).minValue(1).maxValue(0).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> IntegerMeta.builder().isManual(false).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> IntegerMeta.builder().isManual(true).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> IntegerMeta.builder().isManual(true).manualValues(List.of()).build().validCheck(""));
    }

    @Provide
    Arbitrary<Integer> randomIntegerMinus100to100() {
        return Arbitraries.integers().between(-100, 100);
    }

    @Property
    void getRandomValueInNonManual(
            @ForAll("randomIntegerMinus100to100") Integer value1,
            @ForAll("randomIntegerMinus100to100") Integer value2
    ) {
        Integer minValue = value1 > value2 ? value2 : value1;
        Integer maxValue = value1 > value2 ? value1 : value2;

        // non-manual random
        IntegerMeta nonManualMeta = IntegerMeta.builder().isManual(false).minValue(minValue).maxValue(maxValue).build();
        assertThat(nonManualMeta.getRandomValue()).isBetween(minValue, maxValue);
    }

    @Property
    void getRandomValueInManual(@ForAll List<@From("randomIntegerMinus100to100") Integer> listOfIntegers) {
        // if listOfIntegers size == 0, it must be occurring error in ValidCheck method.
        if (listOfIntegers.size() == 0)
            return;

        // manual random
        IntegerMeta manualMeta = IntegerMeta.builder().isManual(true).manualValues(listOfIntegers).build();
        assertThat(manualMeta.getRandomValue()).isIn(listOfIntegers);
    }
}