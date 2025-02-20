package src.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import src.datatypes.Tuple5;
import src.datatypes.Pair;

public class FileProcessing {
    public Tuple5 readInput(String fileName) {
        int N, M, P;
        int currLine = 1;
        List<Pair<Character, List<List<List<Integer>>>>> pieces = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            // Baca baris pertama (N, M, P)
            if (!scanner.hasNextLine()){
                System.out.println("Input kosong pada line " + currLine + ".");
                return null;
            }
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                System.out.println("Input kosong pada line " + currLine + ".");
                return null;
            }
            String[] parts = line.split(" ");
            if (parts.length != 3) {
                System.out.println("Input tidak sesuai format pada line " + currLine + ".");
                return null;
            }
            N = Integer.parseInt(parts[0]);
            M = Integer.parseInt(parts[1]);
            P = Integer.parseInt(parts[2]);
            if (N < 1 || M < 1 || P < 1) {
                System.out.println("Input tidak valid pada line " + currLine + ".");
                return null;
            }
            currLine++;

            // Baca board type
            if (!scanner.hasNextLine()){
                System.out.println("Board type tidak ditemukan pada line " + currLine + ".");
                return null;
            }
            String boardType = scanner.nextLine().trim();
            if (!boardType.equals("DEFAULT") && !boardType.equals("CUSTOM")) {
                System.out.println("Input tidak sesuai format pada line " + currLine + ".");
                return null;
            }
            currLine++;

            int[][] board = new int[N][M];
            if (boardType.equals("DEFAULT")) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        board[i][j] = 0;
                    }
                }
            } else if (boardType.equals("CUSTOM")) {
                for (int i = 0; i < N; i++) {
                    if (!scanner.hasNextLine()) {
                        System.out.println("Input board kurang, diharapkan ada baris untuk board pada line " + currLine + ".");
                        return null;
                    }
                    line = scanner.nextLine();
                    String boardLine = line;
                    if (boardLine.length() < M) {
                        boardLine = String.format("%-" + M + "s", boardLine);
                    } else if (boardLine.length() > M) {
                        boardLine = boardLine.substring(0, M);
                    }
                    for (int j = 0; j < M; j++) {
                        char c = boardLine.charAt(j);
                        if (c == '.') {
                            board[i][j] = -1;
                        } else if (c == 'X') {
                            board[i][j] = 0;
                        } else {
                            System.out.println("Error: Hanya boleh ada '.' atau 'X' pada board di line " + currLine + ".");
                            return null;
                        }
                    }
                    currLine++;
                }
            }

            // Baca seluruh baris piece dan simpan nomor barisnya
            List<String> pieceLines = new ArrayList<>();
            List<Integer> pieceLineNumbers = new ArrayList<>();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (!line.isEmpty()) {
                    pieceLines.add(line);
                    pieceLineNumbers.add(currLine);
                }
                currLine++;
            }

            int index = 0;
            boolean[] vis = new boolean[26];
            Arrays.fill(vis, false);

            // Baca piece sebanyak P
            for (int count = 0; count < P; count++) {
                if (index >= pieceLines.size()) {
                    System.out.println("Piece kurang dari yang diharapkan. Diharapkan " + P + " piece, tetapi hanya ditemukan " + count + ".");
                    return null;
                }
                String firstPieceLine = pieceLines.get(index);
                int firstPieceLineNumber = pieceLineNumbers.get(index);
                if (firstPieceLine.trim().isEmpty()) {
                    System.out.println("Error: Piece tidak sesuai format pada line " + firstPieceLineNumber + ".");
                    return null;
                }
                char id = firstPieceLine.trim().charAt(0);
                if (id < 'A' || id > 'Z') {
                    System.out.println("Error: Piece " + id + " tidak valid pada line " + firstPieceLineNumber + ".");
                    return null;
                }
                if (vis[id - 'A']) {
                    System.out.println("Error: Piece " + id + " sudah ada pada line " + firstPieceLineNumber + ".");
                    return null;
                }
                // Simpan shape piece beserta nomor baris masing-masing
                List<String> shapeStrings = new ArrayList<>();
                List<Integer> shapeLineNumbers = new ArrayList<>();
                shapeStrings.add(firstPieceLine);
                shapeLineNumbers.add(firstPieceLineNumber);
                index++;
                while (index < pieceLines.size()) {
                    String nextLine = pieceLines.get(index);
                    int nextLineNumber = pieceLineNumbers.get(index);
                    if (nextLine.trim().isEmpty()) {
                        index++;
                        continue;
                    }
                    if (nextLine.trim().charAt(0) == id) {
                        shapeStrings.add(nextLine);
                        shapeLineNumbers.add(nextLineNumber);
                        index++;
                    } else {
                        break;
                    }
                }
                int maxLen = 0;
                for (String s : shapeStrings) {
                    maxLen = Math.max(maxLen, s.length());
                }
                List<List<Integer>> shape = new ArrayList<>();
                for (int k = 0; k < shapeStrings.size(); k++) {
                    String s = shapeStrings.get(k);
                    int shapeLineNo = shapeLineNumbers.get(k);
                    List<Integer> row = new ArrayList<>();
                    for (int i = 0; i < maxLen; i++) {
                        if (i < s.length()) {
                            char c = s.charAt(i);
                            if(c == id){
                                row.add(1);
                            } else if (c == ' '){
                                row.add(0);
                            } else {
                                System.out.println("Error: Piece " + id + " tidak valid pada line " + shapeLineNo + ".");
                                return null;   
                            } 
                        }
                        else {
                            row.add(0);
                        }
                    }
                    shape.add(row);
                }
                vis[id - 'A'] = true;
                List<List<List<Integer>>> pi = new ArrayList<>();
                pi.add(shape);
                pieces.add(new Pair<>(id, pi));
            }
            
            // Pastikan tidak ada baris non-kosong tersisa yang tidak terproses
            for (int i = index; i < pieceLines.size(); i++) {
                if (!pieceLines.get(i).trim().isEmpty()) {
                    System.out.println("Error: Input memiliki lebih banyak piece daripada yang diharapkan, terdapat baris non-kosong pada line " + pieceLineNumbers.get(i) + ".");
                    return null;
                }
            }
            
            return new Tuple5(N, M, board, P, pieces);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            return null;
        } catch (Exception e) {
            System.out.println("An error occurred during input processing.");
            e.printStackTrace();
            return null;
        }
    }
}
