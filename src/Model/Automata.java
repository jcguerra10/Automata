package Model;

import Graph.AdjacencyList;
import Graph.Pair;
import Graph.Vertex;

import java.util.*;

public class Automata {

    @SuppressWarnings("rawtypes")
    private final AdjacencyList al;
    private final boolean isMealy;
    private final String[] inputs;
    private final String[][] stateTable;
    private final String initialState;

    @SuppressWarnings("rawtypes")
    public Automata(AdjacencyList al, boolean isMealy, String[] inputs, String[][] stateTable, String initialState) {
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

    @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored", "rawtypes", "ConstantConditions"})
    private ArrayList partition() {
        ArrayList<ArrayList> res = new ArrayList<>();

        HashMap ver = al.getvMap();
        ArrayList keys = new ArrayList(ver.keySet());

        int n = al.getVertex(keys.get(0)).getAdj().size();
        ArrayList<String> transitions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            transitions.add((String) ((Pair) al.getVertex(keys.get(0)).getAdj().get(i)).getID());
        }
        ArrayList listAnswer = new ArrayList();
        for (Object key : keys) {
            String ans = (String) al.getVertex(key).getValue();
            if (!listAnswer.contains(ans))
                listAnswer.add(ans);
        }
        listAnswer.sort(Comparator.naturalOrder());
        res.add(firstPartition(listAnswer, keys));

        boolean op = true;
        while (op) {
            ArrayList<ArrayList> partition = new ArrayList<>();
            ArrayList<ArrayList> befPartition = res.get(res.size() - 1);
            res.add(partition);
            //
            partition = compareBlocks(befPartition, partition, transitions);
            op = comparison(befPartition, partition);
        }

        if (isMealy) {
            ArrayList<ArrayList> mealy = res.get(res.size() - 1);
            ArrayList<ArrayList> pn = new ArrayList();
            for (int i = 0; i < mealy.size() / 2; i++) {
                mealy.get(i);
                ArrayList<String> block = new ArrayList();
                for (int j = 0; j < mealy.get(i).size(); j++) {
                    String state = ((String) mealy.get(i).get(j)).split(",")[0];
                    block.add(state);
                }
                pn.add(block);
            }
            return pn;
        } else {
            return res.get(res.size() - 1);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private boolean comparison(ArrayList<ArrayList> befPartition, ArrayList<ArrayList> partition) {
        boolean op = false;
        if (befPartition.size() == partition.size() && befPartition.size() > 0) {
            for (ArrayList list : befPartition) {
                list.sort(Comparator.comparing(e -> ((String) e)));
            }
            for (ArrayList arrayList : partition) {
                arrayList.sort(Comparator.comparing(e -> ((String) e)));
            }
            for (int i = 0; i < partition.size() && !op; i++) {
                for (int j = 0; j < partition.size(); j++) {
                    if (partition.get(i).get(0).equals(befPartition.get(j).get(0))) {
                        for (int l = 0; l < partition.get(i).size(); l++) {

                            op = !partition.get(i).get(l).equals(befPartition.get(i).get(l));
                        }
                    }
                }
            }
        }
        return op;
    }

    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls", "rawtypes"})
    private ArrayList<ArrayList> compareBlocks(ArrayList<ArrayList> befPartition, ArrayList<ArrayList> partition, ArrayList<String> transitions) {
        for (int i = 0; i < befPartition.size(); i++) {
            ArrayList<String> aux = new ArrayList();
            ArrayList equivalentList = befPartition.get(i);
            if (equivalentList.size() > 1) {
                for (int j = 1; j < equivalentList.size(); j++) {
                    Vertex first = al.getVertex(equivalentList.get(0));
                    Vertex oth = al.getVertex(equivalentList.get(j));
                    for (int k = 0; k < transitions.size(); k++) {
                        String keyPair = (String) ((Pair) first.getAdj().get(k)).getVertex().getKey();
                        String keyPair1 = (String) ((Pair) oth.getAdj().get(k)).getVertex().getKey();
                        int numReject = 0;
                        for (ArrayList listStates : befPartition) {
                            if (!(listStates.contains(keyPair) && listStates.contains(keyPair1))) {
                                numReject++;
                            }
                        }
                        if (numReject == befPartition.size() && !(aux.contains(oth.getKey())))
                            aux.add((String) oth.getKey());
                    }

                }
                ArrayList notExit = new ArrayList<>();
                for (Object o : equivalentList) {
                    if (!(aux.contains(o))) {
                        notExit.add(o);
                    }
                }
                partition.add(notExit);
                if (aux.size() > 0)
                    partition.add(aux);
            } else {
                partition.add(equivalentList);
            }
        }
        return partition;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private ArrayList firstPartition(ArrayList listAnswer, ArrayList keys) {
        ArrayList<ArrayList> partition = new ArrayList();
        for (Object o : listAnswer) {
            ArrayList<String> listEquivalent = new ArrayList();
            for (Object key : keys) {
                String ans = (String) al.getVertex(key).getValue();
                if (ans.equals(o))
                    listEquivalent.add((String) al.getVertex(key).getKey());
            }
            partition.add(listEquivalent);
        }
        return partition;
    }

    @SuppressWarnings({"unchecked", "rawtypes", "ConstantConditions"})
    private String[][] generateTableMealy(ArrayList<ArrayList> partition) {
        String[][] states = new String[partition.size()-1][inputs.length + 1];
        String b = "|Q";

        System.arraycopy(inputs, -1, states[0], 0, states[0].length);
        for (int i = 0; i < states.length; i++) {
            states[i][0] = b + i + "|";
        }
        int j = 2;
        for (ArrayList p : partition) {
            if (p.contains(initialState)) {
                p.add("|Q1|");
            } else {
                String temp = b + j + "|";
                p.add(temp);
                j += 1;
            }
        }
        states = addOriginalStateTableMealy(states, partition);
        return states;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private String[][] addOriginalStateTableMealy(String[][] states, ArrayList<ArrayList> partition) {
        for (int i = 0; i < partition.size(); i++) {
            ArrayList<String> p = partition.get(i);
            states[i + 1][0] = p.get(p.size() - 1);

            String statePartition = p.get(0);

            int s = 0;
            for (int j = 1; j < stateTable.length; j++) {
                if (stateTable[j][0].equals(statePartition)) {
                    s = j;
                }
            }

            for (int j = 1; j < stateTable[0].length; j++) {

                String adjacentState = stateTable[s][j].split(",")[0];
                String response = stateTable[s][j].split(",")[1];

                for (ArrayList<String> par : partition) {
                    if (par.contains(adjacentState)) {
                        states[i + 1][j] = par.get(par.size() - 1) + " , " + response;
                    }
                }
            }
        }
        return states;
    }

    @SuppressWarnings({"ConstantConditions", "unchecked", "rawtypes"})
    private String[][] generateTableMoore(ArrayList<ArrayList> partition) {
        String[][] states = new String[partition.size() + 1][inputs.length + 2];
        String b = "|Q";

        if (states[0].length - 1 - 1 >= 0) System.arraycopy(inputs, 0, states[0], 1, states[0].length - 1 - 1);

        for (int i = 1; i < states.length; i++) {
            states[i][0] = b + i + "|";
        }

        int j = 2;
        for (ArrayList p : partition) {

            if (p.contains(initialState)) {
                states[1][states[0].length - 1] = (String) al.getVertex(initialState).getValue();
                p.add("|Q1|");
            } else {
                String temp = b + j + "|";
                p.add(temp);
                j += 1;
            }
        }
        states = addOriginalStateTableMoore(states, partition);
        return states;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private String[][] addOriginalStateTableMoore(String[][] states, ArrayList<ArrayList> partition) {
        int l = 1;
        int r = 1;
        for (ArrayList<String> p : partition) {

            states[r][0] = p.get(p.size() - 1);
            r++;
            ArrayList<Pair> adj = al.getVertex(p.get(0)).getAdj();
            for (Pair adjacent : adj) {
                for (int j = 1; j < states[0].length - 1; j++)
                    if (states[0][j].equals(adjacent.getID())) {

                        boolean ended = false;
                        for (int k = 0; k < partition.size() && !ended; k++) {
                            if (partition.get(k).contains(adjacent.getVertex().getKey())) {
                                ended = true;
                                states[l][j] = (String) partition.get(k).get(partition.get(k).size() - 1);
                                states[l][states[0].length - 1] = (String) al.getVertex(p.get(0)).getValue();
                            }
                        }
                    }
            }
            l++;
        }
        return states;
    }
}
