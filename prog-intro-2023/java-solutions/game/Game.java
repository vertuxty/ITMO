package game;


import java.util.ArrayList;

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;

//    private final int countOfWins;

    private int firstPlayerWin = 0;
    private int secondPlayerWin = 0;
//    private final ArrayList<Player> listOfPlayer;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
//        this.listOfPlayer = listOfPlayer;
        this.player1 = player1;
        this.player2 = player2;
//        this.countOfWins = countOfWins;
    }

    public int play(boolean log) { //Если мультиплеер, то проходимя по списку.
        while (true) {
            final int result1 = makeMove(player1, 1, log);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = makeMove(player2, 2, log);
            if (result2 != -1) {
                return result2;
            }
//            for (int i = 0; i < listOfPlayer.size(); i++) {
//                final int result = makeMove(listOfPlayer.get(i), i + 1, log);
//                if (result != -1) {
//                    return result;
//                }
//            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        Move move;
        GameResult result;
        while (true) {
            move = player.makeMove(board.getPosition());
            result = board.makeMove(move);
            if (result == GameResult.EXTRAMOVE) {
                if (log) {
                    System.out.println();
                    System.out.println("Player: " + no);
                    System.out.println(move);
                    System.out.println(board);
                    System.out.println("Result: " + result);
                }
            } else {
                break;
            }
        }

        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }

        switch (result) {
            case WIN:
                return no;
            case LOOSE:
                return 3 - no;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
