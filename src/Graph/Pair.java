package Graph;

public class Pair<T> {
    @SuppressWarnings("rawtypes")
    private Vertex vertex;
    private double w;
    private T ID;

    @SuppressWarnings("rawtypes")
    public Pair(Vertex vertex, double w, T ID) {
        this.vertex = vertex;
        this.w = w;
        this.ID = ID;
    }

    @SuppressWarnings("rawtypes")
    public Vertex getVertex() {
        return vertex;
    }

    @SuppressWarnings("rawtypes")
    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public T getID() {
        return ID;
    }

    public void setID(T ID) {
        this.ID = ID;
    }
}
