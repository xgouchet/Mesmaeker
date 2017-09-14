package fr.xgouchet.mesmaeker.mockito;

/**
 * @author Xavier F. Gouchet
 */
public interface OngoingClause<TX, O> {

    OngoingClause<TX, O> thenReturn(O value);

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"})
    OngoingClause<TX, O> thenReturn(O value, O... values);

    OngoingClause<TX, O> thenThrow(Throwable... throwables);

    OngoingClause<TX, O> thenThrow(Class<? extends Throwable> throwableType);

    // Additional method helps users of JDK7+ to hide heap pollution / unchecked generics array creation
    @SuppressWarnings({"unchecked", "varargs"})
    OngoingClause<TX, O> thenThrow(Class<? extends Throwable> toBeThrown, Class<? extends Throwable>... nextToBeThrown);

    // TODO OngoingClause<TX, O> thenAnswer(Answer<?> answer);
    // TODO OngoingClause<TX, O> then(Answer<?> answer);

}
