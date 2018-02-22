package fr.xgouchet.mesmaeker.mockito

/**
 * @author Xavier F. Gouchet
 */
interface OngoingClause<TX, O> {

    fun thenReturn(value: O): OngoingClause<TX, O>

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    fun thenReturn(value: O, vararg values: O): OngoingClause<TX, O>

    fun thenThrow(vararg throwables: Throwable): OngoingClause<TX, O>

    fun thenThrow(throwableType: Class<out Throwable>): OngoingClause<TX, O>

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    fun thenThrow(toBeThrown: Class<out Throwable>, vararg nextToBeThrown: Class<out Throwable>): OngoingClause<TX, O>

    // TODO OngoingClause<TX, O> thenAnswer(Answer<?> answer);
    // TODO OngoingClause<TX, O> then(Answer<?> answer);

}
