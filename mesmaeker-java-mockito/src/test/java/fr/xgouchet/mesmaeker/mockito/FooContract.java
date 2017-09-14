package fr.xgouchet.mesmaeker.mockito;

import fr.xgouchet.mesmaeker.core.Clause;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author Xavier F. Gouchet
 */
@SuppressWarnings("Convert2MethodRef")
public class FooContract extends MockitoContract<Foo> {

    @Clause
    public void active() {
        applyIfImplementation(foo -> foo.setActive(true));

        when(input -> input.bar()).thenReturn("Bar");
        when(input -> input.baz()).thenReturn(42, 41, 40);

        when(input -> input.spam()).thenThrow(new NumberFormatException(), new ConcurrentModificationException(), new ArrayIndexOutOfBoundsException());

        doNothing().when(input -> input.eggs());
        doReturn(42, 41, 40).when(input -> input.bacon());
    }

    @Clause @SuppressWarnings("unchecked")
    public void inactive() {
        applyIfImplementation(foo -> foo.setActive(false));

        when(input -> input.bar()).thenReturn(null);
        when(input -> input.baz()).thenThrow(IllegalStateException.class);

        when(input -> input.spam()).thenThrow(ClassCastException.class, UnsupportedOperationException.class, NoSuchElementException.class);


        //noinspection unchecked
        doThrow(NumberFormatException.class).when(input -> input.eggs());
        doThrow(new ClassCastException("Hey yah")).when(input -> input.bacon());
    }

}
