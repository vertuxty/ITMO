import java.util.ArrayList;
import java.util.Scanner;

public class AlcoGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String first_player = sc.nextLine();
        String second_player = sc.nextLine();
        ArrayList<Integer> cards_first = new ArrayList<>();
        ArrayList<Integer> cards_second = new ArrayList<>();
        Scanner in1 = new Scanner(first_player);
        Scanner in2 = new Scanner(second_player);
        while (in1.hasNextInt()) {
            cards_first.add(in1.nextInt());
            cards_second.add(in2.nextInt());
        }
        int round = 1;
        while (round < 1000000) {
            int firts = cards_first.get(0);
            int second = cards_second.get(0);
            cards_second.remove(0);
            cards_first.remove(0);
            if (firts == 0 && second == 9) {
                cards_first.add(firts);
                cards_first.add(second);
            } else if (second == 0 && firts == 9) {
                cards_second.add(firts);
                cards_second.add(second);
            } else if (firts > second) {
                cards_first.add(firts);
                cards_first.add(second);
            } else {
                cards_second.add(firts);
                cards_second.add(second);
            }
            if (cards_second.size() == 0) {
                System.out.println("first " + round);
                break;
            }
            if (cards_first.size() == 0) {
                System.out.println("second " + round);
                break;
            }
            round++;
        }
    }
}
