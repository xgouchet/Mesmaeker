package fr.xgouchet.mesmaeker.mockito

import java.util.function.Function

import fr.xgouchet.mesmaeker.core.ValidatingUtils.verifyFunctionThrows

/**
 * @author Xavier F. Gouchet
 */
class ValidatingOngoingClause<T, O>
/**
 * @param instance
 * @param function
 */
internal constructor(private val subject: T,
                     private val function: (T) -> O) : OngoingClause<T, O> {

    override fun thenReturn(value: O): OngoingClause<T, O> {
        val actual = function(subject)
        require(actual == value, { "Oups" })
        return this
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    override fun thenReturn(value: O,
                            vararg values: O)
            : OngoingClause<T, O> {
        var actual = function(subject)
        require(actual == value, { "Oups" })

        for (nextValue in values) {
            actual = function(subject)
            require(actual == nextValue, { "Oups" })
        }
        return this
    }

    override fun thenThrow(vararg throwables: Throwable)
            : OngoingClause<T, O> {
        for (t in throwables) {
            verifyFunctionThrows(subject, function, t)
        }
        return this
    }

    override fun thenThrow(throwableType: Class<out Throwable>)
            : OngoingClause<T, O> {
        verifyFunctionThrows(subject, function, throwableType)
        return this
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    override fun thenThrow(toBeThrown: Class<out Throwable>,
                           vararg nextToBeThrown: Class<out Throwable>)
            : OngoingClause<T, O> {
        verifyFunctionThrows(subject, function, toBeThrown)

        for (t in nextToBeThrown) {
            verifyFunctionThrows(subject, function, t)
        }
        return this
    }
}
