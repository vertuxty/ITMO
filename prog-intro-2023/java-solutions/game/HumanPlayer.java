package game;

import java.lang.reflect.Field;
import java.util.Scanner;

public class HumanPlayer extends AbstarctValPlayers implements Player {
    private final Scanner in;
//    private int winsOfPlayer;
//    private final String number;
    public HumanPlayer(Scanner in, int m, int n, int k, int winsOfPlayer, String number) {
        super(m, n, k, winsOfPlayer, number);
        this.in = in;
    }

    private boolean isValid(int Row, int Col) {
        return 0 <= Row && Row < m
                && 0 <= Col && Col < n;
//                && turn == move.getValue();
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());
        int nextRow = 0;
        int nextCol = 0;
        Cell copyCell = null;
        while (true) { //Проверка на валидность ввода данных пользователя (Вводятся цифры, а не буквы и тд.).
            String nextR = in.next();
            String nextC = in.next();
            try {
                nextRow = Integer.valueOf(nextR);
                nextCol = Integer.valueOf(nextC);

//                System.out.print(copyCell);
                if (isValid(nextRow - 1, nextCol- 1)) {
                    copyCell = position.getCell(nextRow - 1, nextCol - 1);
                    if (copyCell == Cell.E) {
                        break;
                    } else {
                        System.out.println("This Cell is not Empty! Try again: ");
                    }
                } else {
                    System.out.println("Out of bounds! Try again: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid inputs! Use digits, not words! Try again: ");
            }
        }

        return new Move(nextRow - 1, nextCol - 1, position.getTurn());
    }
    @Override
    public void setWinsOfPlayer(int winsOfPlayer) {
        this.winsOfPlayer = winsOfPlayer;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public int getWinsOfPlayer() {
        return winsOfPlayer;
    }
}