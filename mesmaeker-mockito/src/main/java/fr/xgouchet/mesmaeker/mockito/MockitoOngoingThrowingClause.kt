package fr.xgouchet.mesmaeker.mockito

import org.mockito.Mockito

/**
 * @author Xavier F. Gouchet
 */
class MockitoOngoingThrowingClause<T> : OngoingThrowingClause<T> {

    private val subject: T
    private val expectedThrowables: Array<Throwable>


    constructor(subject: T, toBeThrown: Array<Throwable>) {
        this.subject = subject
        expectedThrowables = toBeThrown
    }

    constructor(subject: T, vararg toBeThrown: Class<out Throwable>) {
        this.subject = subject
        expectedThrowables = listOf(*toBeThrown)
                .map { it.newInstance() }
                .toTypedArray()
    }

    override fun `when`(block: (T) -> Unit): OngoingThrowingClause<T> {
        return whenever(block)
    }

    override fun whenever(block: (T) -> Unit): OngoingThrowingClause<T> {
        val stubber = Mockito.doThrow(*expectedThrowables).`when`(subject)
        block(stubber)
        return this
    }
}
