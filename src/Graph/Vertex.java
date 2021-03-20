package Graph;

import java.util.ArrayList;

public class Vertex<K, V> {
    public K key;
    public V value;

    @SuppressWarnings("rawtypes")
    public ArrayList<Pair> pairs;

    public Vertex(K key) {
        this.key = key;
        value = null;
        pairs = new ArrayList<>();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
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
}
