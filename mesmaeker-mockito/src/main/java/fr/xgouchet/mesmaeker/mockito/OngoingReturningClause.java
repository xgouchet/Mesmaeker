package fr.xgouchet.mesmaeker.mockito;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Xavier F. Gouchet
 */
public interface OngoingReturningClause<T, O> extends OngoingReverseClause<T> {


    OngoingReturningClause<T, O> when(Function<T, O> function);

}
