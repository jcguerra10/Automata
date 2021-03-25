package Graph;

import java.util.ArrayList;

public class Vertex<K, V> {
    public final static int INFINITE = Integer.MAX_VALUE;
    public final static char WHITE = 'W';
    public final static char GRAY = 'G';
    public final static char BLACK = 'B';

    private char color;
    private Vertex parent;
    private int distance;
    private ArrayList<Pair> adj;
    private boolean initial;

    public K key;
    public V value;

    @SuppressWarnings("rawtypes")
    public ArrayList<Pair> pairs;

    public Vertex(K key) {
        this.key = key;
        value = null;
        pairs = new ArrayList<>();
        initial = false;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
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
        pairs.add(new Pair<>(vertice, peso, null));

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addPareja(Vertex vertice, double peso, String ruta) {
        pairs.add(new Pair(vertice, peso, ruta));
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public ArrayList<Pair> getAdj() {
        return adj;
    }

    public void setAdj(ArrayList<Pair> adj) {
        this.adj = adj;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }
}
