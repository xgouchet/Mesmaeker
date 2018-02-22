package fr.xgouchet.mesmaeker.mockito

import fr.xgouchet.mesmaeker.core.BaseContract
import org.mockito.Mockito


/**
 * TODO add javadoc !
 */
abstract class MockitoContract<T>(val mockedClass: Class<T>) : BaseContract<T>() {

    override fun instantiateMock(): T {
        return Mockito.mock(mockedClass)
    }

    fun <O> whenever(function: (T) -> O): OngoingClause<T, O> {
        return if (isInstanceMock) {
            MockitoOngoingClause(getMock(), function)
        } else {
            ValidatingOngoingClause(getInstance(), function)
        }
    }

    fun <O> `when`(function: (T) -> O): OngoingClause<T, O> {
        return if (isInstanceMock) {
            MockitoOngoingClause(getMock(), function)
        } else {
            ValidatingOngoingClause(getInstance(), function)
        }
    }

    fun doThrow(vararg throwables: Throwable): OngoingThrowingClause<T> {
        return if (isInstanceMock) {
            MockitoOngoingThrowingClause(getMock(), arrayOf(*throwables))
        } else {
            ValidatingOngoingThrowingClause(getInstance(), *throwables)
        }
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    fun doThrow(vararg throwableClasses: Class<out Throwable>): OngoingThrowingClause<T> {
        return if (isInstanceMock) {
            MockitoOngoingThrowingClause(getMock(), *throwableClasses)
        } else {
            ValidatingOngoingThrowingClause(getInstance(), *throwableClasses)
        }
    }

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    fun <O> doReturn(value: O, vararg values: O): OngoingReturningClause<T, O> {
        return if (isInstanceMock) {
            MockitoOngoingReturningClause(getMock(), value, *values)
        } else {
            ValidatingOngoingReturningClause(getInstance(), value, *values)
        }
    }

    fun doNothing(): OngoingVoidClause<T> {
        return if (isInstanceMock) {
            MockitoOngoingVoidClause(getMock())
        } else {
            ValidatingOngoingVoidClause(getInstance())
        }
    }


    // TODO public static <O> OngoingThrowingClause<T, O> doAnswer(Answer answer) {    }
}
