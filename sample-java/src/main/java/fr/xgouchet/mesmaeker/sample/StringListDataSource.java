package fr.xgouchet.mesmaeker.sample;

import java.util.ArrayList;

class StringListDataSource implements DataSource {

    private final ArrayList<String> data = new ArrayList<>();


    @Override
    public int getDataSet() {
        return 0;
    }

    @Override
    public String getDataAt(int position) {
        if (position < 0 || position >= data.size()) {
            throw new IndexOutOfBoundsException("Wrong!");
        }
        return data.get(position);
    }

    @Override
    public int size() {
        return data.size();
    }

    public void ensureSize(int size, String defaultValue) {
        for (int i = data.size(); i < size; ++i) {
            data.add(defaultValue);
        }
    }

    public void set(int position, String value) {
        data.set(position, value);
    }
}
