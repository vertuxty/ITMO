package game;


public class Matches extends AbstaractValuesMNK {

    private Player player1;
    private Player player2;
    private Player playerSwitch;
    private int countOfWins;
    private StringBuilder sb;

    public Matches(int m, int n, int k, int countOfWins, Player player1, Player player2, StringBuilder sb) {
        super(m, n, k);
        this.countOfWins = countOfWins;
        this.player1 = player1;
        this.player2 = player2;
        this.sb = sb;
    }
    public void Mathes() {
        while (player1.getWinsOfPlayer() < countOfWins && player2.getWinsOfPlayer() < countOfWins) {
            final int result = new Game(
                    new mnkBoard(m, n, k, sb.toString()),
                    player1, player2
            ).play(true);

            switch (result) {
                case 1 -> System.out.println(player1.getNumber() +  " player won round number" );
                case 2 -> System.out.println(player2.getNumber() + " player won this round number " );
                case 0 -> System.out.println("In round number " + "" + " Draw");
                default -> throw new AssertionError("Unknown result " + result);
            }

            if (result == 1) {
                player1.setWinsOfPlayer(player1.getWinsOfPlayer() + 1);
            }
            if (result == 2) {
                player2.setWinsOfPlayer(player2.getWinsOfPlayer() + 1);
            }
            if (player1.getWinsOfPlayer() >= countOfWins || player2.getWinsOfPlayer() >= countOfWins) {
                break;
            } else {
                playerSwitch = player1;
                player1 = player2;
                player2 = playerSwitch;
            }
        }

        if (player1.getWinsOfPlayer() == countOfWins) {
            System.out.println("First player win this game!!!!!");
        } else {
            System.out.println("Second player win this game!!!!!");
        }
        System.out.println("Final table: \n Second player: " + player2.getWinsOfPlayer() + "\n First Player: " + player1.getWinsOfPlayer());
    }
}
