package game;

import java.util.Random;

public class RandomPlayer extends AbstarctValPlayers implements Player {

    private final Random random = new Random();
//    private int winsOfPlayer;
    public RandomPlayer(int m, int n, int k, int winsOfPlayer, String number) {
        super(m, n, k, winsOfPlayer, number);
    }

    @Override
    public Move makeMove(Position position) {
        while (true) {
            final Move move = new Move(
                    random.nextInt(m),
                    random.nextInt(n),
                    position.getTurn()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public int getWinsOfPlayer() {
        return winsOfPlayer;
    }

    @Override
    public void setWinsOfPlayer(int winsOfPlayer) {
        this.winsOfPlayer = winsOfPlayer;
    }

    @Override
    public String getNumber() {
        return number;
    }
}