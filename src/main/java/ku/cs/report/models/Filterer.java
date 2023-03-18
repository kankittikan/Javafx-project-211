package ku.cs.report.models;

public interface Filterer<T> {
    boolean filter(T t);
}
