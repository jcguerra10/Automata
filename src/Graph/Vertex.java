package Graph;

import java.util.ArrayList;

public class Vertex<K, V> {
    public final static int INFINITE = Integer.MAX_VALUE;
    public final static char WHITE = 'W';
    public final static char GRAY = 'G';
    public final static char BLACK = 'B';

    private char color;
    @SuppressWarnings("rawtypes")
    private Vertex parent;
    private int distance;
    @SuppressWarnings("rawtypes")
    private final ArrayList<Pair> adj;
    private boolean initial = false;

    public K key;
    public V value;

    @SuppressWarnings("rawtypes")
    public ArrayList<Pair> pairs;

    public Vertex(K key) {
        this.key = key;
        parent = null;
        value = null;
        pairs = new ArrayList<>();
        adj = new ArrayList<>();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList<Pair> getPairs() {
        return pairs;
    }

    @SuppressWarnings("rawtypes")
    public void addPareja(Vertex vertice, double peso) {
        adj.add(new Pair<>(vertice, peso, null));

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addPareja(Vertex vertice, double peso, String ruta) {
        adj.add(new Pair(vertice, peso, ruta));
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    @SuppressWarnings("rawtypes")
    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList<Pair> getAdj() {
        return adj;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }
}
