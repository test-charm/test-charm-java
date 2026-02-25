package org.testcharm.map.bug;

import org.junit.jupiter.api.Test;
import org.testcharm.map.Mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SourceClassShouldBePublic {

    @Test
    void source_class_should_be_public() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> new Mapper("org.testcharm.invalidmap.bug"));

        assertThat(runtimeException).hasMessage("org.testcharm.invalidmap.bug.PackagePrivateSource should be public");
    }
}
