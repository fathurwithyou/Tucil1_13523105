import java.util.Scanner;

import src.datatypes.Tuple5;
import src.models.FileProcessing;
import src.models.IQPuzzleSolver;

public class Main {
    public static void main(String[] args) {
        FileProcessing fileProcessing = new FileProcessing();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Masukkan filepath: ");
        String filePath = scanner.nextLine();
        Tuple5 input = fileProcessing.readInput("test/" + filePath);
        
        if (input == null) {
            return;
        }
        
        IQPuzzleSolver solver = new IQPuzzleSolver();
        solver.main(input);
    }
}
