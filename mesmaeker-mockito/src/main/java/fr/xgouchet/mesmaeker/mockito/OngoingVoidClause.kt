package fr.xgouchet.mesmaeker.mockito

/**
 * @author Xavier F. Gouchet
 */
interface OngoingVoidClause<T> : OngoingReverseClause<T> {

    fun `when`(block: (T) -> Unit): OngoingVoidClause<T>

    fun whenever(block: (T) -> Unit): OngoingVoidClause<T>

}
