package airTravel;

import java.util.List;
import java.util.Scanner;

public class ScannerWork {
    private Scanner scanner = new Scanner(System.in);

    public char getChar(){
        return scanner.next().charAt(0);
    }

    public char getCharFromRange(List<String> nummbersWithMistakes) {
        boolean flag = false;
        char sign;
        do {
            System.out.println("Wybierz: a, b, c... = ?");
            sign = getChar();
            if (sign >= 'a' && sign < ('a' + nummbersWithMistakes.size())) {
                flag = true;
            }
        } while (!flag);
        return sign;
    }
}
