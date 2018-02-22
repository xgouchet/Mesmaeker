package fr.xgouchet.mesmaeker.core


/**
 * @author Xavier F. Gouchet
 */
object ValidatingUtils {

    @JvmStatic
    fun <T, O> verifyFunctionThrows(subject: T,
                                    function: (T) -> O,
                                    expected: Throwable) {
        var match = false
        var thrown: Throwable? = null
        try {
            function(subject)
        } catch (t: Throwable) {
            thrown = t
            match = expected.javaClass.isAssignableFrom(t.javaClass)
        }

        if (thrown == null) {
            throw AssertionError("Expecting " + expected.javaClass.simpleName + " to be thrown")
        } else if (!match) {
            throw AssertionError("Expecting " + expected.javaClass.simpleName + " to be thrown, but was " + thrown.javaClass.simpleName)
        }
    }

    @JvmStatic
    fun <T, O> verifyFunctionThrows(subject: T,
                                    function: (T) -> O,
                                    expected: Class<out Throwable>) {
        var match = false
        var thrown: Throwable? = null
        try {
            function(subject)
        } catch (t: Throwable) {
            thrown = t
            match = expected.isAssignableFrom(t.javaClass)
        }

        if (thrown == null) {
            throw AssertionError("Expecting " + expected.simpleName + " to be thrown")
        } else if (!match) {
            throw AssertionError("Expecting " + expected.simpleName + " to be thrown, but was " + thrown.javaClass.simpleName)
        }
    }

    @JvmStatic
    fun <T> verifyBlockThrows(subject: T, block: (T) -> Unit, expected: Class<out Throwable>) {
        var match = false
        var thrown: Throwable? = null
        try {
            block(subject)
        } catch (t: Throwable) {
            thrown = t
            match = expected.isAssignableFrom(t.javaClass)
        }

        if (thrown == null) {
            throw AssertionError("Expecting " + expected.simpleName + " to be thrown")
        } else if (!match) {
            throw AssertionError("Expecting " + expected.simpleName + " to be thrown, but was " + thrown.javaClass.simpleName)
        }
    }
}
