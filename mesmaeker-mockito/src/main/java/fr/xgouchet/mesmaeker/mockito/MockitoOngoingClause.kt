package fr.xgouchet.mesmaeker.mockito

import org.mockito.Mockito

import java.util.function.Function

/**
 * @author Xavier F. Gouchet
 */
class MockitoOngoingClause<T, O>(private val subject: T,
                                 private val function: (T) -> O)
    : OngoingClause<T, O> {

    override fun thenReturn(value: O): OngoingClause<T, O> {
        Mockito.`when`(function(subject)).thenReturn(value)
        return this
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    override fun thenReturn(value: O, vararg values: O): OngoingClause<T, O> {
        Mockito.`when`(function(subject)).thenReturn(value, *values)
        return this
    }

    override fun thenThrow(vararg throwables: Throwable): OngoingClause<T, O> {
        Mockito.`when`(function(subject)).thenThrow(*throwables)
        return this
    }

    override fun thenThrow(throwableType: Class<out Throwable>): OngoingClause<T, O> {
        Mockito.`when`(function(subject)).thenThrow(throwableType)
        return this
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    override fun thenThrow(toBeThrown: Class<out Throwable>, vararg nextToBeThrown: Class<out Throwable>): OngoingClause<T, O> {
        Mockito.`when`(function(subject)).thenThrow(toBeThrown, *nextToBeThrown)
        return this
    }
}
