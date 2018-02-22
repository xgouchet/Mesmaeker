package fr.xgouchet.mesmaeker.mockito

import java.util.function.Consumer
import java.util.function.Function

/**
 * @author Xavier F. Gouchet
 */
interface OngoingReturningClause<T, O> : OngoingReverseClause<T> {


    fun `when`(function: Function<T, O>): OngoingReturningClause<T, O>

    fun whenever(function: Function<T, O>): OngoingReturningClause<T, O>
}
