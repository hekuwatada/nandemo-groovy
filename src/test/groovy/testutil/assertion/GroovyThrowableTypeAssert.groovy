package testutil.assertion

import org.assertj.core.api.ThrowableAssertAlternative

import static org.assertj.core.api.Assertions.assertThat
import static org.assertj.core.api.Assertions.fail

/**
 * A small extension to AssertJ with Groovy Closure in Groovy < 3 which does not support Java 8 lambda.
 */
class GroovyThrowableTypeAssert<T extends Throwable> {
    private Class<T> expectedThrowableType

    ThrowableAssertAlternative<T> isThrownBy(codeBlockUnderTest) {
        try {
            codeBlockUnderTest()
            fail("${this.expectedThrowableType.toString()} Throwable was expected but not thrown.")
            null // Forced hack due to lack of language type support such as likes of Option/Try/Either
        } catch (AssertionError ae) {
            throw ae
        } catch (T actual) {
            assertThat(actual).isExactlyInstanceOf(this.expectedThrowableType) // Redundant test to have a successful assertion
            new ThrowableAssertAlternative<T>(actual)
        } catch (Throwable th) {
            assertThat(th).isExactlyInstanceOf(this.expectedThrowableType) // To get pre-formatted assertion error
            null // Forced hack
        }
    }

    static <T extends Throwable> GroovyThrowableTypeAssert<T> assertThat(Class<T> expectedThrowableType) {
        new GroovyThrowableTypeAssert<T>(expectedThrowableType: expectedThrowableType)
    }
}


