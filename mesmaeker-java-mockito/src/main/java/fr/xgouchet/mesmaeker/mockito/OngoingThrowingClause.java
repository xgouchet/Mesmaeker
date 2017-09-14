package fr.xgouchet.mesmaeker.mockito;

import java.util.function.Consumer;

/**
 * @author Xavier F. Gouchet
 */
public interface OngoingThrowingClause<T> extends OngoingReverseClause<T> {


    OngoingThrowingClause<T> when(Consumer<T> consumer);
}
