package Ui;

import Model.Automata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. for Mealy \n" +
                "2. for Moore");
        boolean isMealy = sc.nextLine().equals("1");

        System.out.println("Number of states: ");
        int rows = Integer.parseInt(sc.nextLine());
        System.out.println("Number of Inputs: ");
        int columns = Integer.parseInt(sc.nextLine());

        String[][] matrix;

        if (isMealy){
            matrix = new String[columns + 1][rows + 1];
        }else{
            matrix = new String[columns + 1][rows + 2];
        }

        System.out.println("we start to fill the table");
        String fst = sc.nextLine();
        String[] sp = fst.split(";");
        String[] inputs = sp;
        System.arraycopy(sp, 0, matrix[0], 1, sp.length);
        for (int i = 1; i < matrix.length; i++) {
            fst = sc.nextLine();
            sp = fst.split(";");
            System.arraycopy(sp, 0, matrix[i], 0, matrix[0].length);
        }
        System.out.println("What is the initialState");
        String initialState = sc.nextLine();
        Automata a = new Automata(isMealy, inputs, matrix, initialState);
        String[][] res = a.getMinimumConnectedAutomaton();

        showArray(res, a);
    }

    private static void showArray(String[][] res, Automata a) {

        String[] in = a.getInputs();
        System.out.print("|--|");
        for (String s : in) {
            if (!a.isMealy())
                System.out.print("|" + s + " |");
            else
                System.out.print("|" + s + " |    ");
        }
        System.out.println();
        for (int i = 1; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(res[i][j]);
            }
            System.out.println();
        }
    }
}
