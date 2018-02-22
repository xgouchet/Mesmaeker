package fr.xgouchet.mesmaeker.mockito

/**
 * @author Xavier F. Gouchet
 */
class ValidatingOngoingVoidClause<T>(private val subject: T)
    : OngoingVoidClause<T> {

    override fun `when`(block: (T) -> Unit): OngoingVoidClause<T> {
        return whenever(block)
    }

    override fun whenever(block: (T) -> Unit): OngoingVoidClause<T> {
        block(subject)
        return this
    }
}
