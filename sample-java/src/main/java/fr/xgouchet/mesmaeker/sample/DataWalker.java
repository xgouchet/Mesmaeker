package fr.xgouchet.mesmaeker.sample;

interface DataWalker<T> {

    void walkForward();

    void walkBackward();

    void walkTo(int position);

    T getCurrent();

    boolean hasNext();

    T peekNext();

    void setLoopEnabled(boolean enabled);

}
