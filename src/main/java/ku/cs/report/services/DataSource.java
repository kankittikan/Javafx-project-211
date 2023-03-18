package ku.cs.report.services;

public interface DataSource<T> {
    T readData();

    void writeData(T t);
}
