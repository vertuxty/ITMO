package game;

import java.util.Arrays;
import java.util.Map;

public class mnkBoard extends AbstaractValuesMNK implements Board, Position {

    public boolean getCellIsEmpty(int nextRow, int nextCol) {
        return (field[nextRow][nextCol] == Cell.E);
    }

    public Cell[][] getter() {
        return field;
    }
    private final String str;
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );

    private final Cell[][] field = new Cell[m][n];

//    private static final Cell[][] copyField;
    private Cell turn;
//    private final int countOfWins;

    public mnkBoard(int m, int n, int k, String str) {
        super(m, n, k); //Передаем в конструктор информацию о размере поля.
        this.str = str;
//        this.countOfWins = countOfWins;
//        this.countOfPlayers = countOfPlayers;
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }



    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            if (!(0 <= move.getRow() && move.getRow() < m && 0 <= move.getCol() && move.getCol() < n)) {
                throw new AssertionError("Out of Bounds! YOu LOOSE!");
            }
            if (field[move.getRow()][move.getCol()] != Cell.E) {
                throw new AssertionError("This Cell is Not Empty!");
            }
            if ( turn != move.getValue()) {
                throw  new AssertionError("Not your turn!");
            }
            return GameResult.LOOSE;
        }
        field[move.getRow()][move.getCol()] = move.getValue();

        if (checkWin()) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        if (extraMove(move)) {
            return GameResult.EXTRAMOVE;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;

        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() { //Проверка на ничью (из примера кода с лекции меняем 3, на m и n.
        int count = 0;
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (field[r][c] == Cell.E) {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }

    private boolean extraMove(Move move) { //Проверка на доп ход
        int countRowLeft = 0;
        int countRowRight = 0;

        int countColHight = 0;
        int countColLow = 0;

        int diagonalHightLeft = 0;
        int diagonalLowLeft = 0;

        int diagonalHightRight = 0;
        int diagonalLowRight = 0;

        int row = move.getRow();
        int col = move.getCol();

        int colLeft = move.getCol() - 1;
        int colRight = move.getCol() + 1;
        int rowHight = move.getRow() - 1;
        int rowLow = move.getRow() + 1;
        //Поиск по строкам
        while (colLeft >= 0 && field[row][colLeft] == move.getValue()) {
            countRowLeft++;
            colLeft--;
        }

        while (colRight < n && field[row][colRight] == move.getValue()) {
            countRowRight++;
            colRight++;
        }

        if (countRowLeft + countRowRight + 1 >=4) {
            return true;
        }
        //поиск по столбцам
        while (rowHight >= 0 && field[rowHight][col] == move.getValue()) {
            countColHight++;
            rowHight--;
        }

        while (rowLow < m && field[rowLow][col] == move.getValue()) {
            countColLow++;
            rowLow++;
        }

        if (countColHight + countColLow + 1 >= 4) {
            return true;
        }

        //Переопределяю значения для поиска подряд идущих одинаковых символов по диагонали вида: \
        colLeft = col - 1;                                                               //       \
        colRight = col + 1;                                                                   //   \
        rowLow = row + 1;                                                                     //    \
        rowHight = row - 1;

        while (colLeft >= 0 && rowHight >= 0 && field[rowHight][colLeft] == move.getValue()) {
            diagonalHightLeft++;
            colLeft--;
            rowHight--;
        }

        while (colRight < n && rowLow < m && field[rowLow][colRight] == move.getValue()) {
            diagonalLowRight++;
            colRight++;
            rowLow++;
        }

        if (diagonalLowRight + diagonalHightLeft + 1 >= 4) {
            return true;
        }
        //Переопределяю переменные для поиска 4-х и более подряд идущих символов по диагонали вида:
        colLeft = col - 1;                                                               //          /
        colRight = col + 1;                                                              //         /
        rowLow = row + 1;                                                                //        /
        rowHight = row - 1;                                                              //       /

        while (colLeft >=0 && rowLow < m && field[rowLow][colLeft] == move.getValue()) {
            diagonalLowLeft++;
            colLeft--;
            rowLow++;
        }

        while (colRight < n && rowHight >=0 && field[rowHight][colRight] == move.getValue()) {
            diagonalHightRight++;
            colRight++;
            rowHight--;
        }

        if (diagonalHightRight + diagonalLowLeft + 1 >= 4) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        for (int r = 0; r < m; r++) {
            int count = 0;
            for (int c = 0; c < n; c++) {
                if (field[r][c] == turn) {
                    count++;
                    if (count == k) { //добавили доп проверку
                        return true;
                    }
                } else { // как и тут
                    count = 0;
                }
            }
            if (count == k) {
                return true;
            }
        }

        for (int c = 0; c < n; c++) {
            int count = 0;
            for (int r = 0; r < m; r++) {
                if (field[r][c] == turn) {
                    count++;
                    if (count == k) { //та же доп проверочка
                        return true;
                    }
                } else {
                    count = 0; // аналогично
                }
            }
            if (count == k) {
                return true;
            }
        }

        for (int r = 0; r < m; r++) { // Проверка для диагоналей, которые идут так:      0
            for (int c = 0; c < n; c++) {                                         //    0
                int count = 0;                                                    //   0
                int diffRow = r;                                                  //  0
                int diffCol = c;
                while (diffRow < m && diffCol >= 0) {
                    if (field[diffRow][diffCol] == turn) {
                        count++;
                        if (count == k) {
                            return true;
                        }
                    } else {
                        count = 0;
                    }
                    diffRow++;
                    diffCol--;
                }
                if (count == k) {
                    return true;
                }
            }
        }

        for (int r = m - 1; r >= 0; r--) {
            for (int c = n - 1; c >=0 ; c--) {
                int count = 0;
                int diffRow = r;
                int diffCol = c;
                while (diffRow >= 0 && diffCol >= 0) {
                    if (field[diffRow][diffCol] == turn) {
                        count++;
                        if (count == k) {
                            return true;
                        }
                    } else {
                        count = 0;
                    }
                    diffRow--;
                    diffCol--;
                }
                if (count == k) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getCol() && move.getCol() < n
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }


    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    public Cell[][] getField() {
        return field;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();//Ввести  123...
        sb.append(" ");
        for (int i = 0; i < n; i++) {
            sb.append("_");
        }
        sb.append(System.lineSeparator());
        for (int r = 0; r < m; r++) {
            sb.append("|");
            for (Cell cell : field[r]) {
                sb.append(CELL_TO_STRING.get(cell));
            }
            sb.append("|");
            sb.append(r + 1);
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
