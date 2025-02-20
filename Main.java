import java.util.Scanner;

import src.datatypes.Tuple5;
import src.models.FileProcessing;
import src.models.IQPuzzleSolver;
import src.models.ImageGenerator;

public class Main {
    public static void main(String[] args) {
        FileProcessing fileProcessing = new FileProcessing();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan filepath: ");
        String filePath = scanner.nextLine();
        Tuple5 input = fileProcessing.readInput(filePath);

        if (input == null) {
            return;
        }

        IQPuzzleSolver solver = new IQPuzzleSolver();
        solver.main(input);

        Scanner sc = new Scanner(System.in);
        System.out.print("Apakah Anda ingin menyimpan hasil? (y/n) ");
        String ans = sc.next();
        if (!ans.equals("y") && !ans.equals("Y") && !ans.equals("ya") && !ans.equals("Ya")) {
            return;
        }

        System.out.println();
        if (solver.f) {
            ImageGenerator imgGen = new ImageGenerator(solver.r, solver.c, solver.b);
            imgGen.genImg(fileProcessing.getOutputFilenameCleaned());


            fileProcessing.writeOutput(solver.it, solver.b, solver.exTime);
        } else {
            fileProcessing.writeOutput(solver.it, solver.b, solver.exTime);
        }
    }
}
