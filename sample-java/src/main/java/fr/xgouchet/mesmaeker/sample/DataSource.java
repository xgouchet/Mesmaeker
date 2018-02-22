package fr.xgouchet.mesmaeker.sample;

interface DataSource {

    int getDataSet();

    String getDataAt(int position);

    int size();
}
