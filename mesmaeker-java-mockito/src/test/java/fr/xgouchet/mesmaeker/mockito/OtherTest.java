package fr.xgouchet.mesmaeker.mockito;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Xavier F. Gouchet
 */
public class OtherTest {

    private FooContract fooContract;

    @Before
    public void setUp() {
        fooContract = new FooContract();
        fooContract.useMockInstance(Foo.class);
    }

    @Test
    public void testActiveBar() {
        fooContract.active();
        Foo mock = fooContract.getMock();

        // Single return
        assertThat(mock.bar()).isEqualTo("Bar");
        assertThat(mock.bar()).isEqualTo("Bar");
    }


    @Test
    public void testActiveBaz() {
        fooContract.active();
        Foo mock = fooContract.getMock();

        // Multiple return
        assertThat(mock.baz()).isEqualTo(42);
        assertThat(mock.baz()).isEqualTo(41);
        assertThat(mock.baz()).isEqualTo(40);
    }

    @Test
    public void testActiveSpam() {
        fooContract.active();
        Foo mock = fooContract.getMock();

        // Multiple throwable
        try {
            mock.spam();
            throw new AssertionError("Expected NumberFormatException");
        } catch (NumberFormatException e) {
        }

        try {
            mock.spam();
            throw new AssertionError("Expected ConcurrentModificationException");
        } catch (ConcurrentModificationException e) {
        }

        try {
            mock.spam();
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }


    @Test
    public void testInactiveBar() {
        fooContract.inactive();
        Foo mock = fooContract.getMock();

        // return null
        assertThat(mock.bar()).isNull();
    }

    @Test(expected = IllegalStateException.class)
    public void testInactiveBaz() {
        fooContract.inactive();
        Foo mock = fooContract.getMock();

        mock.baz();
    }

    @Test
    public void testInactiveSpam() {
        fooContract.inactive();
        Foo mock = fooContract.getMock();

        // Multiple throwable
        try {
            mock.spam();
            throw new AssertionError("Expected ClassCastException");
        } catch (ClassCastException e) {
        }

        try {
            mock.spam();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }

        try {
            mock.spam();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void testActiveEggs() {
        fooContract.active();

        fooContract.getMock().eggs();

    }

    @Test(expected = NumberFormatException.class)
    public void testInactiveEggs() {
        fooContract.inactive();

        fooContract.getMock().eggs();
    }


    @Test
    public void testActiveBacon() {
        fooContract.active();

        int result = fooContract.getMock().bacon();

        assertThat(result).isEqualTo(42);
    }

    @Test(expected = ClassCastException.class)
    public void testInactiveBacon() {
        fooContract.inactive();

        fooContract.getMock().bacon();
    }


}
