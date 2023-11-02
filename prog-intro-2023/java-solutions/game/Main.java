package game;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = 0;
        int n = 0;
        int k = 0;
        int countOfWins = 0;
        try {
            while (true) { //Вводим числа, попутно обрабатывая ошибки неправельного ввода от пользователя.
                System.out.println("Write size of board (m - number of rows, n - number of colums) and k - number of matching symbols required to win in a row: ");
                String strM = sc.next();
                String strN = sc.next();
                String strK = sc.next();
                String strCountOfWins = sc.next();
                try {
                    m = Integer.parseInt(strM);
                    n = Integer.parseInt(strN);
                    k = Integer.parseInt(strK);
                    countOfWins = Integer.parseInt(strCountOfWins);
                    if (m < 1 | n < 1 | k < 1 | countOfWins < 1) {
                        throw new Exception("Use positive number");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Expression. Input 'm', 'n', 'k' as digits, not words. Try again: ");
                } catch (Exception e) {
                    System.out.println("Use positive number");
                }
            }

            StringBuilder sb = new StringBuilder(" ");
            for (int i = 1; i < n + 1; i++) {
                sb.append(i);
            }

            Player player1 = new HumanPlayer(new Scanner(System.in), m, n, k, 0, "first");
            Player player2 = new HumanPlayer(new Scanner(System.in), m, n, k, 0, "second");
            Matches match = new Matches(m, n, k, countOfWins, player1, player2, sb);
            match.Mathes();
        } catch (NoSuchElementException e) {
            System.out.println("Out of game");
        }
    }
}
