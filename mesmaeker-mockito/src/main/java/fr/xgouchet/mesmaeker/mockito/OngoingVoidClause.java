package fr.xgouchet.mesmaeker.mockito;

import java.util.function.Consumer;

/**
 * @author Xavier F. Gouchet
 */
public interface OngoingVoidClause<T> extends OngoingReverseClause<T> {

    OngoingVoidClause<T> when(Consumer<T> consumer);

}
