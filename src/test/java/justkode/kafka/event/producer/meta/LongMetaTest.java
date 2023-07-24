package justkode.kafka.event.producer.meta;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LongMetaTest {

    @Test
    void validCheck() {
        assertDoesNotThrow(() -> LongMeta.builder().isManual(false).minValue(1L).maxValue(3L).build().validCheck(""));
        assertDoesNotThrow(() -> LongMeta.builder().isManual(false).minValue(1L).maxValue(1L).build().validCheck(""));
        assertDoesNotThrow(() -> LongMeta.builder().isManual(true).manualValues(List.of(1L,2L,3L,4L)).build().validCheck(""));

        assertThrows(RuntimeException.class, () -> LongMeta.builder().isManual(false).minValue(1L).maxValue(0L).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> LongMeta.builder().isManual(false).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> LongMeta.builder().isManual(true).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> LongMeta.builder().isManual(true).manualValues(List.of()).build().validCheck(""));
    }

    @Provide
    Arbitrary<Long> randomLongMinus100to100() {
        return Arbitraries.longs().between(-100L, 100L);
    }

    @Property
    void getRandomValueInNonManual(
            @ForAll("randomLongMinus100to100") Long value1,
            @ForAll("randomLongMinus100to100") Long value2
    ) {
        Long minValue = value1 > value2 ? value2 : value1;
        Long maxValue = value1 > value2 ? value1 : value2;

        // non-manual random
        LongMeta nonManualMeta = LongMeta.builder().isManual(false).minValue(minValue).maxValue(maxValue).build();
        assertThat(nonManualMeta.getRandomValue()).isBetween(minValue, maxValue);
    }

    @Property
    void getRandomValueInManual(@ForAll List<@From("randomLongMinus100to100") Long> listOfLongs) {
        // if listOfIntegers size == 0, it must be occurring error in ValidCheck method.
        if (listOfLongs.size() == 0)
            return;

        // manual random
        LongMeta manualMeta = LongMeta.builder().isManual(true).manualValues(listOfLongs).build();
        assertThat(manualMeta.getRandomValue()).isIn(listOfLongs);
    }
}