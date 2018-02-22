package fr.xgouchet.mesmaeker.mockito

import fr.xgouchet.mesmaeker.core.ValidatingUtils.verifyBlockThrows

/**
 * @author Xavier F. Gouchet
 */
class ValidatingOngoingThrowingClause<T> : OngoingThrowingClause<T> {


    private val subject: T
    private val expectedThrowables: List<Class<out Throwable>>


    @SafeVarargs
    constructor(subject: T, vararg toBeThrown: Class<out Throwable>) {
        this.subject = subject
        expectedThrowables = listOf(*toBeThrown)
    }

    constructor(subject: T, vararg toBeThrown: Throwable) {
        this.subject = subject

        expectedThrowables = listOf(*toBeThrown)
                .map { it.javaClass }
    }

    override fun `when`(block: (T) -> Unit): OngoingThrowingClause<T> {
        return whenever(block)
    }

    override fun whenever(block: (T) -> Unit): OngoingThrowingClause<T> {
        for (t in expectedThrowables) {
            verifyBlockThrows(subject, block, t)
        }
        return this
    }
}
