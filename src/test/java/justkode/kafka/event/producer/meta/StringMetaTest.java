package justkode.kafka.event.producer.meta;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringMetaTest {

    @Test
    void validCheck() {
        assertDoesNotThrow(() -> StringMeta.builder().isManual(false).minLength(1).maxLength(3).build().validCheck(""));
        assertDoesNotThrow(() -> StringMeta.builder().isManual(false).minLength(1).maxLength(1).build().validCheck(""));
        assertDoesNotThrow(() -> StringMeta.builder().isManual(true).manualValues(List.of("a", "b", "c", "d")).build().validCheck(""));

        assertThrows(RuntimeException.class, () -> StringMeta.builder().isManual(false).minLength(1).maxLength(0).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> StringMeta.builder().isManual(false).minLength(-1).maxLength(1).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> StringMeta.builder().isManual(false).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> StringMeta.builder().isManual(true).build().validCheck(""));
        assertThrows(RuntimeException.class, () -> StringMeta.builder().isManual(true).manualValues(List.of()).build().validCheck(""));

    }

    @Provide
    Arbitrary<Integer> randomLength1to100() {
        return Arbitraries.integers().between(1, 100);
    }

    @Provide
    Arbitrary<String> randomString() {
        return Arbitraries.strings().ofMinLength(1).ofMaxLength(100);
    }

    @Property
    void getRandomValueInNonManual(
            @ForAll("randomLength1to100") Integer value1,
            @ForAll("randomLength1to100") Integer value2
    ) {
        Integer minLength = value1 > value2 ? value2 : value1;
        Integer maxLength = value1 > value2 ? value1 : value2;

        // non-manual random
        StringMeta nonManualMeta = StringMeta.builder().isManual(false).minLength(minLength).maxLength(maxLength).build();
        assertThat(nonManualMeta.getRandomValue().length()).isBetween(minLength, maxLength);
    }

    @Property
    void getRandomValueInManual(@ForAll List<@From("randomString") String> listOfStrings) {
        // if listOfIntegers size == 0, it must be occurring error in ValidCheck method.
        if (listOfStrings.size() == 0)
            return;

        // manual random
        StringMeta manualMeta = StringMeta.builder().isManual(true).manualValues(listOfStrings).build();
        assertThat(manualMeta.getRandomValue()).isIn(listOfStrings);
    }
}