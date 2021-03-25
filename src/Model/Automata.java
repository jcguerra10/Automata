package Model;

import Graph.AdjacencyList;
import Graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;

public class Automata {

    @SuppressWarnings("rawtypes")
    private final AdjacencyList al;
    private final boolean isMealy;
    private final String[] inputs;
    private final String[] stateTable;
    private final String initialState;

    @SuppressWarnings("rawtypes")
    public Automata(AdjacencyList al, boolean isMealy, String[] inputs, String[] stateTable, String initialState) {
        this.al = al;
        this.isMealy = isMealy;
        this.inputs = inputs;
        this.stateTable = stateTable;
        this.initialState = initialState;
    }

    @SuppressWarnings({"rawtypes", "unchecked", "MismatchedQueryAndUpdateOfCollection"})
    private void fillMealy(String[][] matrix) {
        ArrayList transitions = new ArrayList();
        ArrayList state = new ArrayList();
        ArrayList<String> ans = new ArrayList<>();

        Collections.addAll(transitions, matrix[0]);
        for (String[] strings : matrix) {
            state.add(strings[0]);
        }



        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                String st = matrix[i][j].split(",")[1];
                if (!ans.contains(st))
                    ans.add(st);
            }
        }
        addVertexMealy(matrix, ans, state);
        addEdgeMealy(matrix, ans, state);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addVertexMealy(String[][] matrix, ArrayList<String> ans, ArrayList state) {
        boolean flat = true;
        for (int i = 1; i < matrix.length; i++) {
            for (String an : ans) {

                String nameState = state.get(i) + "," + an;
                Vertex<String, String> vAdd = new Vertex<>(nameState);
                vAdd.setValue(an + "");
                if (flat) {
                    vAdd.setInitial(true);
                    flat = false;
                }
                al.addVertex(vAdd);
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addEdgeMealy(String[][] matrix, ArrayList<String> ans, ArrayList state) {
        for (int i = 1; i < matrix.length; i++) {
            for (String an : ans) {
                String nameState = state.get(i) + "," + an;
                for (int k = 1; k < matrix[0].length; k++) {
                    al.addEdge(nameState, matrix[i][k], 0, matrix[0][k]);
                }
            }
        }
    }

    @SuppressWarnings({"rawtypes", "MismatchedQueryAndUpdateOfCollection", "unchecked"})
    private void fillMoore(String[][] matrix) {
        ArrayList transitions = new ArrayList();
        ArrayList state = new ArrayList();
        ArrayList ans = new ArrayList();

        Collections.addAll(transitions, matrix[0]);
        for (String[] strings : matrix) {
            state.add(strings[0]);
            ans.add(strings[matrix.length - 1]);
        }
        addVertexMoore(matrix, ans, state);
        addEdgeMoore(matrix, state);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addVertexMoore(String[][] matrix, ArrayList<String> ans, ArrayList state) {
        boolean flat = true;
        for (int i = 1; i < matrix.length; i++) {
            String nameState = (String) state.get(i);
            Vertex<String, String> vAdd = new Vertex<>(nameState);
            vAdd.setValue(ans.get(i) + "");
            if (flat) {
                vAdd.setInitial(true);
                flat = false;
            }
            al.addVertex(vAdd);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void addEdgeMoore(String[][] matrix, ArrayList state) {
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length - 1; j++) {
                String nameState = (String) state.get(i);
                al.addEdge(nameState, matrix[i][j], 0, matrix[0][j]);
            }
        }
    }
}
