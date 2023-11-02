package game;

public abstract class AbstarctValPlayers extends AbstaractValuesMNK {

    protected int winsOfPlayer;
    protected final String number;
    public AbstarctValPlayers(int m, int n, int k, int winsOfPlayer, String number) {
        super(m, n, k);
        this.winsOfPlayer = winsOfPlayer;
        this.number = number;
    }
}
