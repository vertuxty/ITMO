package game;

public interface Player {
    Move makeMove(Position position);
    int getWinsOfPlayer();

    void setWinsOfPlayer(int winsOfPlayer);
    String getNumber();
}
