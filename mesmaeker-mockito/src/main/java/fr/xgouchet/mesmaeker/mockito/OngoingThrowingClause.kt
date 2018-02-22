package fr.xgouchet.mesmaeker.mockito

/**
 * @author Xavier F. Gouchet
 */
interface OngoingThrowingClause<T> : OngoingReverseClause<T> {


    fun `when`(block: (T) -> Unit): OngoingThrowingClause<T>

    fun whenever(block: (T) -> Unit): OngoingThrowingClause<T>
}
