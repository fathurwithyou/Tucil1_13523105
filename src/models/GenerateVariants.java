package src.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import src.datatypes.Pair;

public class GenerateVariants {
    // Fungsi untuk merotasi 90 derajat
    private List<List<Integer>> rotate90(List<List<Integer>> p) {
        int n = p.size();
        int m = p.get(0).size();
        List<List<Integer>> r = new ArrayList<>();
        for (int j = 0; j < m; j++) {
            List<Integer> row = new ArrayList<>();
            for (int i = n - 1; i >= 0; i--) {
                row.add(p.get(i).get(j));
            }
            r.add(row);
        }
        return r;
    }

    // Fungsi untuk merefleksi
    private List<List<Integer>> reflect(List<List<Integer>> p) {
        int n = p.size();
        int m = p.get(0).size();
        List<List<Integer>> r = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = m - 1; j >= 0; j--) {
                row.add(p.get(i).get(j));
            }
            r.add(row);
        }
        return r;
    }

    // Fungsi untuk mengubah list of list of integer menjadi string
    private String varToString(List<List<Integer>> s) {
        StringBuilder sb = new StringBuilder();
        for (List<Integer> row : s) {
            for (Integer cell : row) {
                sb.append(cell).append(",");
            }
            sb.append(";");
        }
        return sb.toString();
    }

    // Fungsi untuk menghasilkan semua variasi unik dari sebuah piece
    public List<Pair<Character, List<List<List<Integer>>>>> generateVariants(
            List<Pair<Character, List<List<List<Integer>>>>> ps) {
        List<Pair<Character, List<List<List<Integer>>>>> vs = new ArrayList<>();
        for (Pair<Character, List<List<List<Integer>>>> p : ps) {
            Character id = p.fi;
            List<List<Integer>> b = p.se.get(0);
            List<List<List<Integer>>> vl = new ArrayList<>();
            Set<String> sn = new HashSet<>();

            List<List<Integer>> r0 = b;
            List<List<Integer>> r90 = rotate90(r0);
            List<List<Integer>> r180 = rotate90(r90);
            List<List<Integer>> r270 = rotate90(r180);
            List<List<Integer>> rf0 = reflect(b);
            List<List<Integer>> rf90 = rotate90(rf0);
            List<List<Integer>> rf180 = rotate90(rf90);
            List<List<Integer>> rf270 = rotate90(rf180);

            List<List<List<Integer>>> av = new ArrayList<>();
            av.add(r0);
            av.add(r90);
            av.add(r180);
            av.add(r270);
            av.add(rf0);
            av.add(rf90);
            av.add(rf180);
            av.add(rf270);

            // Menghilangkan variasi yang sama
            for (List<List<Integer>> v : av) {
                String c = varToString(v);
                if (!sn.contains(c)) {
                    sn.add(c);
                    vl.add(v);
                }
            }
            vs.add(new Pair<>(id, vl));
        }
        return vs;
    }
}
