package fr.xgouchet.mesmaeker.mockito

import java.util.function.Function


/**
 * @author Xavier F. Gouchet
 */
class ValidatingOngoingReturningClause<T, O>(private val subject: T,
                                             private val value: O,
                                             vararg values: O)
    : OngoingReturningClause<T, O> {

    private val nextValues: List<O> = listOf(*values)

    override fun `when`(function: Function<T, O>): OngoingReturningClause<T, O> {
        return whenever(function)
    }

    override fun whenever(function: Function<T, O>): OngoingReturningClause<T, O> {
        var actual = function.apply(subject)
        require(actual == value, { "Expected <$actual> to be <$value>" })

        for (nextValue in nextValues) {
            actual = function.apply(subject)
            require(actual == nextValue, { "Expected <$actual> to be <$nextValue>" })
        }
        return this
    }
}
