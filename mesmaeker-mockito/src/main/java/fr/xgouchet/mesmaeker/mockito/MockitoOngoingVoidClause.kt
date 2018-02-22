package fr.xgouchet.mesmaeker.mockito

import org.mockito.Mockito

/**
 * @author Xavier F. Gouchet
 */
class MockitoOngoingVoidClause<T>(private val subject: T) : OngoingVoidClause<T> {

    override fun `when`(block: (T) -> Unit): OngoingVoidClause<T> {
        return whenever(block)
    }

    override fun whenever(block: (T) -> Unit): OngoingVoidClause<T> {
        val stubber = Mockito.doNothing().`when`(subject)
        block(stubber)
        return this
    }
}
