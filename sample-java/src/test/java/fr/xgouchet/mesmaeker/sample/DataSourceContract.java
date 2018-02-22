package fr.xgouchet.mesmaeker.sample;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import fr.xgouchet.elmyr.Forger;
import fr.xgouchet.mesmaeker.annotations.Clause;
import fr.xgouchet.mesmaeker.mockito.MockitoContract;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@SuppressWarnings("unchecked")
public class DataSourceContract<T extends DataSource> extends MockitoContract<T> {

    public final Forger forger = new Forger();

    public DataSourceContract() {
        super((Class<T>) DataSource.class);
    }

    public DataSource getTypedMock() {
        return getMock();
    }

    @Clause
    public void withSize(final int size) {
        applyIfImplementation(new Function1<T, Unit>() {
            @Override
            public Unit invoke(T dataSource) {
                initWithSize(dataSource, size);
                return Unit.INSTANCE;
            }
        });

        whenever(new Function1<T, Integer>() {
            @Override
            public Integer invoke(T dataSource) {
                return dataSource.size();
            }
        }).thenReturn(size);
        whenever(new Function1<T, String>() {
            @Override
            public String invoke(T dataSource) {
                return dataSource.getDataAt(-1);
            }
        }).thenThrow(new IndexOutOfBoundsException());
        whenever(new Function1<T, String>() {
            @Override
            public String invoke(T dataSource) {
                return dataSource.getDataAt(size);
            }
        }).thenThrow(new IndexOutOfBoundsException());
    }

    @Clause
    public void withDataAt(final int position, final String data) {
        applyIfImplementation(new Function1<T, Unit>() {
            @Override
            public Unit invoke(T dataSource) {
                putData(dataSource, position, data);
                return Unit.INSTANCE;
            }
        });
        whenever(new Function1<DataSource, String>() {
            @Override
            public String invoke(DataSource dataSource) {
                return dataSource.getDataAt(position);
            }
        }).thenReturn(data);
    }

    @Nullable
    @Override
    public Object[] generateParameters(@NotNull String contractClause) {
        switch (contractClause) {
            case "withSize":
                return new Object[]{forger.aSmallInt()};
            case "withDataAt":
                return new Object[]{forger.aTinyInt(), forger.aString()};
        }
        return new Object[0];
    }

    void initWithSize(T source, int size) {

    }

    void putData(T dataSource, int position, String data) {

    }
}
