package assertion

import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.fail
import static testutil.assertion.GroovyThrowableTypeAssert.assertThat as gAssertThat

class GroovyThrowableTypeAssertTest {

    @Test
    void isThrownByAssertsThatExpectedThrowableWasThrown() {
        gAssertThat(IllegalArgumentException.class).isThrownBy {
            throw new IllegalArgumentException("test exception")
        }.withMessage("test exception")
    }

    @Test
    void isThrownByPassThroughAssertionException() {
        String expectedMessage = "failed assertion"
        try {
            gAssertThat(IllegalArgumentException.class).isThrownBy { assertThat(false).as(expectedMessage).isTrue() }
            fail("AssertionError must be thrown.")
        } catch (AssertionError ae) {
            assertThat(ae).hasMessageContaining(expectedMessage)
        }
    }

    @Test
    void isThrownByFailsWhenThrowableTypeIsSubtype() {
        String expectedMessage = "super exception type"
        try {
            gAssertThat(IllegalArgumentException.class).isThrownBy { throw new RuntimeException(expectedMessage) }
            fail("AssertionError must be thrown.")
        } catch (AssertionError ae) {
            assertThat(ae).withFailMessage(expectedMessage)
        }
    }

    @Test
    void isThrownByFailsWhenThrowableTypeIsDifferent() {
        String expectedMessage = "different exception type"
        try {
            gAssertThat(IllegalArgumentException.class).isThrownBy { throw new Exception(expectedMessage) }
            fail("AssertionError must be thrown.")
        } catch (AssertionError ae) {
            assertThat(ae).withFailMessage(expectedMessage)
        }
    }

    @Test
    void isThrownByFailsWhenNullIsThrown() {
        try {
            gAssertThat(IllegalArgumentException.class).isThrownBy { throw null }
            fail("AssertionError must be thrown.")
        } catch (AssertionError ae) {
            assertThat(ae).hasMessageContaining("NullPointerException")
        }
    }
}
