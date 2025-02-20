package src.models;

import src.datatypes.Tuple5;
import src.datatypes.Pair;
import java.util.List;
import java.util.Stack;

public class IQPuzzleSolver {
    private boolean f = false;
    private boolean[] vis = new boolean[26];
    private int r, c, p;
    private int[][] b;
    private List<Pair<Character, List<List<List<Integer>>>>> ps;
    private int it = 0;

    private static class Pos {
        int[][] b;
        boolean[] vis;
        int rem;

        public Pos(int[][] b, boolean[] u, int rem) {
            this.b = b;
            this.vis = u;
            this.rem = rem;
        }
    }

    public IQPuzzleSolver() {}

    private int[][] cpy(int[][] o) {
        int rs = o.length;
        int cs = o[0].length;
        int[][] c = new int[rs][cs];
        for (int i = 0; i < rs; i++) {
            System.arraycopy(o[i], 0, c[i], 0, cs);
        }
        return c;
    }

    private int[] fz(int[][] b) {
        int rs = b.length;
        int cs = b[0].length;
        for (int i = 0; i < rs; i++) {
            for (int j = 0; j < cs; j++) {
                if (b[i][j] == 0)
                    return new int[] { i, j };
            }
        }
        return null;
    }

    private boolean isSafe(int[][] b, int x, int y, List<List<Integer>> v) {
        int rs = v.size();
        int cs = v.get(0).size();
        int br = b.length;
        int bc = b[0].length;
        for (int i = 0; i < rs; i++) {
            for (int j = 0; j < cs; j++) {
                if (v.get(i).get(j) == 1) {
                    int bx = x + i;
                    int by = y + j;
                    if (bx < 0 || bx >= br || by < 0 || by >= bc || b[bx][by] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void pl(int[][] b, int x, int y, List<List<Integer>> v, Character n) {
        int rs = v.size();
        int cs = v.get(0).size();
        for (int i = 0; i < rs; i++) {
            for (int j = 0; j < cs; j++) {
                if (v.get(i).get(j) == 1) {
                    b[x + i][y + j] = n - 'A' + 1;
                }
            }
        }
    }

    public void solve() {
        Stack<Pos> s = new Stack<>();
        int[][] ib = cpy(b);
        boolean[] iv = vis.clone();
        Pos is = new Pos(ib, iv, p);
        s.push(is);
        Pos ss = null;

        while (!s.isEmpty()) {
            Pos curr = s.pop();
            int[] n = fz(curr.b);
            if (n == null) {
                if (curr.rem == 0) {
                    ss = curr;
                    f = true;
                    break;
                }
                continue;
            }
            int x = n[0];
            int y = n[1];
            for (var pi : ps) {
                Character ch = pi.fi;
                if (!curr.vis[ch - 'A']) {
                    boolean[] nv = curr.vis.clone();
                    nv[ch - 'A'] = true;
                    for (List<List<Integer>> v : pi.se) {
                        int rs = v.size();
                        int cs = v.get(0).size();
                        int bx = x, by = y;
                        boolean fl = false;
                        for (int i = 0; i < rs; i++) {
                            for (int j = 0; j < cs; j++) {
                                it++;
                                if (v.get(i).get(j) == 1) {
                                    bx = x - i;
                                    by = y - j;
                                    fl = true;
                                    break;
                                }
                            }
                            if (fl)
                                break;
                        }
                        if (fl && isSafe(curr.b, bx, by, v)) {
                            int[][] nb = cpy(curr.b);
                            pl(nb, bx, by, v, ch);
                            Pos ns = new Pos(nb, nv, curr.rem - 1);
                            s.push(ns);
                        }
                    }
                }
            }
        }
        if (ss != null) {
            b = ss.b;
        }
    }

    private String getColor(char l) {
        String[] colors = {
            "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m",
            "\u001B[91m", "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m",
            "\u001B[37m", "\u001B[90m", "\u001B[38;5;208m", "\u001B[38;5;45m", "\u001B[38;5;81m",
            "\u001B[38;5;141m", "\u001B[38;5;129m", "\u001B[38;5;87m", "\u001B[38;5;220m",
            "\u001B[38;5;214m", "\u001B[38;5;51m", "\u001B[38;5;201m", "\u001B[38;5;105m",
            "\u001B[38;5;170m"
        };
        int idx = l - 'A';
        if (idx < 0 || idx >= colors.length)
            return "\u001B[0m";
        return colors[idx];
    }

    public void main(Tuple5 in) {
        r = in.N;
        c = in.M;
        p = in.P;
        b = in.board;
        ps = in.piece;

        GenerateVariants gen = new GenerateVariants();
        ps = gen.generateVariants(ps);

        long st = System.nanoTime();
        solve();
        long et = System.nanoTime();

        if (f) {
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (b[i][j] == 0 || b[i][j] == -1) {
                        System.out.print(" ");
                    } else {
                        System.out.print(getColor((char) (b[i][j] + 'A' - 1))
                                + (char) (b[i][j] + 'A' - 1));
                    }
                }
                System.out.println();
            }
            System.out.print("\u001B[0m");

            ImageGenerator imgGen = new ImageGenerator(r, c, b);
            imgGen.genImg("puzzle_solution.png");
        } else {
            System.out.println("No solution");
        }

        double exTime = (et - st) / 1e6;
        System.out.println();
        System.out.println("Execution time: " + exTime + " ms");
        System.out.println("Iterations: " + it);
    }
}
