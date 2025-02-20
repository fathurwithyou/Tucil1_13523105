package src.datatypes;

import java.util.*;

public class Tuple5 {
    public int N;
    public int M;
    public int P;
    public int[][] board;
    public List<Pair<Character, List<List<List<Integer>>>>> piece;

    public Tuple5(int N, int M, int[][] board, int P, List<Pair<Character, List<List<List<Integer>>>>> piece) {
        this.N = N;
        this.M = M;
        this.board = board;
        this.P = P;
        this.piece = piece;
    }

}
