package fr.xgouchet.mesmaeker.mockito

import org.mockito.Mockito

import java.util.function.Function

/**
 * @author Xavier F. Gouchet
 */
class MockitoOngoingReturningClause<T, O>(private val subject: T,
                                          private val value: O,
                                          vararg values: O)
    : OngoingReturningClause<T, O> {

    private val nextValues: List<O> = listOf(*values)

    override fun `when`(function: Function<T, O>): OngoingReturningClause<T, O> {
        return whenever(function)
    }

    override fun whenever(function: Function<T, O>): OngoingReturningClause<T, O> {
        val stubber: T

        if (nextValues.isEmpty()) {
            stubber = Mockito.doReturn(value).`when`(subject)
        } else {
            // ugly trick to expand as varargs
            val array = nextValues.map { it as Any }.toTypedArray()
            stubber = Mockito.doReturn(value, *array).`when`(subject)
        }
        function.apply(stubber)
        return this
    }


}
