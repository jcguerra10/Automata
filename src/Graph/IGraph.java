package Graph;

public interface IGraph<K, V> {
    void addVertex(V v);
    void removeVertex(K k);
    void addEdge(K k1, K k2, double weight);
    void addEdge(K k1, K k2, double weight, String route);
    void removeEdge(K k1, K k2);
}
