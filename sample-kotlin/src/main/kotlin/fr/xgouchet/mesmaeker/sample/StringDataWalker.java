package fr.xgouchet.mesmaeker.sample;

class StringDataWalker implements DataWalker<String> {

    private final DataSource dataSource;

    private boolean loopEnabled = false;
    private int position = 0;

    public StringDataWalker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void walkForward() {
        position++;
        if (position >= dataSource.size()) {
            if (loopEnabled) {
                position = 0;
            } else {
                position = dataSource.size();
            }
        }
    }

    @Override
    public void walkBackward() {
        position--;
        if (position < 0) {
            if (loopEnabled) {
                position = dataSource.size() - 1;
            } else {
                position = -1;
            }
        }
    }

    @Override
    public void walkTo(int position) {
        this.position = position;
    }

    @Override
    public String getCurrent() {
        return dataSource.getDataAt(position);
    }

    @Override
    public boolean hasNext() {
        int nextPosition = getNextPosition();
        return nextPosition < dataSource.size();
    }

    @Override
    public String peekNext() {
        int nextPosition = getNextPosition();
        return dataSource.getDataAt(nextPosition);
    }

    @Override
    public void setLoopEnabled(boolean enabled) {
        this.loopEnabled = enabled;
    }

    private int getNextPosition() {
        int nextPosition = position + 1;
        if (nextPosition >= dataSource.size()) {
            if (loopEnabled) {
                nextPosition = 0;
            } else {
                nextPosition = dataSource.size();
            }
        }
        return nextPosition;
    }
}
