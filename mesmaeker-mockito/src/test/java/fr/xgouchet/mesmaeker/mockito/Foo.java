package fr.xgouchet.mesmaeker.mockito;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author Xavier F. Gouchet
 */
public class Foo {

    private boolean active;
    private int bazCount = 43;
    private int baconCount = 43;
    private int spam = 0;

    private final RuntimeException[] ACTIVE_SPAM = {new NumberFormatException(), new ConcurrentModificationException(), new ArrayIndexOutOfBoundsException()};
    private final RuntimeException[] INACTIVE_SPAM = {new ClassCastException(), new UnsupportedOperationException(), new NoSuchElementException()};

    public void setActive(boolean active) {
        this.active = active;
    }

    public String bar() {
        return active ? "Bar" : null;
    }

    public int baz() {
        if (active) {
            bazCount--;
            return bazCount;
        } else {
            throw new IllegalStateException("Can't call baz when inactive");
        }
    }

    public String spam() {
        spam++;

        if (active) {
            throw ACTIVE_SPAM[(spam - 1) % ACTIVE_SPAM.length];
        } else {
            throw INACTIVE_SPAM[(spam - 1) % INACTIVE_SPAM.length];
        }
    }

    public void eggs() {
        if (!active) {
            throw new NumberFormatException();
        }
    }

    public int bacon() {
        if (active) {
            baconCount--;
            return baconCount;
        } else {
            throw new ClassCastException("Can't call bacon when inactive");
        }
    }
}
