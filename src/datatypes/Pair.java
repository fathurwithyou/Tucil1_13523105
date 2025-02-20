package src.datatypes;

public class Pair<T, U> {
    public T fi;
    public U se;
    
    public Pair(T x, U y) {
        this.fi = x;
        this.se = y;
    }
}
