package Graph;

import java.util.ArrayList;
import java.util.HashMap;

public class AdjacencyList<K, V> implements IGraph<K, V> {

    private final HashMap<K, V> vMap;
    private final boolean managed;
    @SuppressWarnings("rawtypes")
    private static ArrayList arr;

    public AdjacencyList(boolean managed) {
        this.managed = managed;
        vMap = new HashMap<>();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void addVertex(V v) {
        vMap.put((K) ((Vertex) v).getKey(), v);
    }

    @SuppressWarnings({"rawtypes", "unchecked", "SuspiciousListRemoveInLoop"})
    @Override
    public void removeVertex(K k) {
        if (!managed) {

            Vertex vDelete = (Vertex) vMap.get(k);
            ArrayList<Pair> pairsVDelete = vDelete.getPairs();
            for (Pair pair : pairsVDelete) {
                Vertex adj = pair.getVertex();
                ArrayList<Pair> pairsAdj = adj.getPairs();
                for (int j = 0; j < pairsAdj.size(); j++) {
                    if (pairsAdj.get(j).getVertex().equals(vDelete)) {
                        pairsAdj.remove(j);
                    }
                }
            }
        }
        vMap.remove(k);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void addEdge(K k1, K k2, double weight) {
        if (managed) {
            ((Vertex) vMap.get(k1)).addPareja((Vertex) vMap.get(k2), weight);
        } else {
            ((Vertex) vMap.get(k1)).addPareja((Vertex) vMap.get(k2), weight);
            ((Vertex) vMap.get(k1)).addPareja((Vertex) vMap.get(k1), weight);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void addEdge(K k1, K k2, double weight, String route) {
        if (managed) {
            ((Vertex) vMap.get(k1)).addPareja((Vertex) vMap.get(k2), weight, route);
        } else {
            ((Vertex) vMap.get(k1)).addPareja((Vertex) vMap.get(k2), weight, route);
            ((Vertex) vMap.get(k1)).addPareja((Vertex) vMap.get(k1), weight, route);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked", "SuspiciousListRemoveInLoop"})
    @Override
    public void removeEdge(K k1, K k2) {
        ArrayList<Pair> parejas = ((Vertex) vMap.get(k1)).getPairs();
        for (int i = 0; i < parejas.size(); i++) {
            if (parejas.get(i).getVertex().equals(vMap.get(k2))) {
                parejas.remove(i);
            }
        }
        if (!managed) {
            ArrayList<Pair> parejasW = ((Vertex) vMap.get(k2)).getPairs();
            for (int i = 0; i < parejasW.size(); i++) {
                if (parejasW.get(i).getVertex().equals(vMap.get(k1))) {
                    parejasW.remove(i);
                }
            }
        }
    }
}
