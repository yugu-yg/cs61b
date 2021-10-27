package jump61;

import java.util.ArrayDeque;
import java.util.Formatter;

import java.util.Stack;
import java.util.function.Consumer;

import static jump61.Side.*;

/** Represents the state of a Jump61 game.  Squares are indexed either by
 *  row and column (between 1 and size()), or by square number, numbering
 *  squares by rows, with squares in row 1 numbered from 0 to size()-1, in
 *  row 2 numbered from size() to 2*size() - 1, etc. (i.e., row-major order).
 *
 *  A Board may be given a notifier---a Consumer<Board> whose
 *  .accept method is called whenever the Board's contents are changed.
 *
 *  @author Yu
 */
class Board {

    /** An uninitialized Board.  Only for use by subtypes. */
    protected Board() {
        _notifier = NOP;
    }

    /** An N x N board in initial configuration. */
    Board(int N) {
        this();
        _board = new Square[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                _board[r][c] = Square.square(WHITE, 1);
            }
        }
        _history = new Stack<Board>();
        _numMoves = 0;
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear, and whose notifier does nothing. */
    Board(Board board0) {
        this(board0.size());
        this.internalCopy(board0);
        _readonlyBoard = new ConstantBoard(this);
    }

    /** Returns a readonly version of this board. */
    Board readonlyBoard() {
        return _readonlyBoard;
    }

    /** (Re)initialize me to a cleared board with N squares on a side. Clears
     *  the undo history and sets the number of moves to 0. */
    void clear(int N) {
        Board clearBoard = new Board(N);
        _board = clearBoard._board;
        announce();
    }

    /** Copy the contents of BOARD into me. */
    void copy(Board board) {
        internalCopy(board);
        _history = board._history;
        _numMoves = board._numMoves;
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history. Assumes BOARD and I have the same size. */
    private void internalCopy(Board board) {
        assert size() == board.size();
        for (int r = 1; r <= board.size(); r++) {
            for (int c = 1; c <= board.size(); c++) {
                int n = sqNum(r, c);
                this.set(r, c, board.get(n).getSpots(), board.get(n).getSide());
            }
        }
    }

    /** Return the number of rows and of columns of THIS. */
    int size() {
        return _board.length;
    }

    /** Returns the contents of the square at row R, column C
     *  1 <= R, C <= size (). */
    Square get(int r, int c) {
        return get(sqNum(r, c));
    }

    /** Returns the contents of square #N, numbering squares by rows, with
     *  squares in row 1 number 0 - size()-1, in row 2 numbered
     *  size() - 2*size() - 1, etc. */
    Square get(int n) {
        return _board[row(n) - 1][col(n) - 1];
    }

    /** Returns the total number of spots on the board. */
    int numPieces() {
        int sum = 0;
        for (int r = 0; r < size(); r++) {
            for (int c = 0; c < size(); c++) {
                sum += _board[r][c].getSpots();
            }
        }
        return sum;
    }

    /** Returns the Side of the player who would be next to move.  If the
     *  game is won, this will return the loser (assuming legal position). */
    Side whoseMove() {
        return ((numPieces() + size()) & 1) == 0 ? RED : BLUE;
    }

    /** Return true iff row R and column C denotes a valid square. */
    final boolean exists(int r, int c) {
        return 1 <= r && r <= size() && 1 <= c && c <= size();
    }

    /** Return true iff S is a valid square number. */
    final boolean exists(int s) {
        int N = size();
        return 0 <= s && s < N * N;
    }

    /** Return the row number for square #N. */
    final int row(int n) {
        return n / size() + 1;
    }

    /** Return the column number for square #N. */
    final int col(int n) {
        return n % size() + 1;
    }

    /** Return the square number of row R, column C. */
    final int sqNum(int r, int c) {
        return (c - 1) + (r - 1) * size();
    }

    /** Return a string denoting move (ROW, COL)N. */
    String moveString(int row, int col) {
        return String.format("%d %d", row, col);
    }

    /** Return a string denoting move N. */
    String moveString(int n) {
        return String.format("%d %d", row(n), col(n));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
        to square at row R, column C. */
    boolean isLegal(Side player, int r, int c) {
        return isLegal(player, sqNum(r, c));
    }

    /** Returns true iff it would currently be legal for PLAYER to add a spot
     *  to square #N. */
    boolean isLegal(Side player, int n) {
        if (!exists(n)) {
            return false;
        }

        Side side = this.get(n).getSide();
        if (!player.playableSquare(side)) {
            return false;
        }
        return true;
    }

    /** Returns true iff PLAYER is allowed to move at this point. */
    boolean isLegal(Side player) {
        for (int n = 0; n < size() * size(); n++) {
            if (isLegal(player, n)) {
                return true;
            }
        }
        return false;
    }

    /** Returns the winner of the current position, if the game is over,
     *  and otherwise null. */
    final Side getWinner() {
        int N = size() * size();
        if (numOfSide(RED) == N) {
            return RED;
        } else if (numOfSide(BLUE) == N) {
            return BLUE;
        } else {
            return null;
        }
    }

    /** Return the number of squares of given SIDE. */
    int numOfSide(Side side) {
        int num = 0;
        for (int n = 0; n < size() * size(); n++) {
            if (side == get(n).getSide()) {
                num += 1;
            }
        }
        return num;
    }

    /** Add a spot from PLAYER at row R, column C.  Assumes
     *  isLegal(PLAYER, R, C). */
    void addSpot(Side player, int r, int c) {
        markUndo();
        _numMoves += 1;
        addSpot(player, sqNum(r, c));
        announce();
    }

    /** Add a spot from PLAYER at square #N.  Assumes isLegal(PLAYER, N). */
    void addSpot(Side player, int n) {
        if (getWinner() != null) {
            return;
        }
        int spots = get(n).getSpots();
        int neighbours = this.neighbors(n);
        int r = row(n);
        int c = col(n);
        if (spots + 1 > neighbours) {
            this.set(r, c, 1, player);
            if (exists(r, c + 1)) {
                addSpot(player, sqNum(r, c + 1));
            }
            if (exists(r, c - 1)) {
                addSpot(player, sqNum(r, c - 1));
            }
            if (exists(r + 1, c)) {
                addSpot(player, sqNum(r + 1, c));
            }
            if (exists(r - 1, c)) {
                addSpot(player, sqNum(r - 1, c));
            }
        } else {
            this.set(r, c, spots + 1, player);
        }
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white). */
    void set(int r, int c, int num, Side player) {
        internalSet(r, c, num, player);
        announce();
    }

    /** Set the square at row R, column C to NUM spots (0 <= NUM), and give
     *  it color PLAYER if NUM > 0 (otherwise, white).  Does not announce
     *  changes. */
    private void internalSet(int r, int c, int num, Side player) {
        internalSet(sqNum(r, c), num, player);
    }

    /** Set the square #N to NUM spots (0 <= NUM), and give it color PLAYER
     *  if NUM > 0 (otherwise, white). Does not announce changes. */
    private void internalSet(int n, int num, Side player) {
        _board[row(n) - 1][col(n) - 1] = Square.square(player, num);
    }

    /** Undo the effects of one move (that is, one addSpot command).  One
     *  can only undo back to the last point at which the undo history
     *  was cleared, or the construction of this Board. */
    void undo() {
        if (!_history.empty()) {
            this.internalCopy(_history.pop());
        }
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        Board board = new Board(this);
        _history.push(board);
    }

    /** Add DELTASPOTS spots of side PLAYER to row R, column C,
     *  updating counts of numbers of squares of each color. */
    private void simpleAdd(Side player, int r, int c, int deltaSpots) {
        internalSet(r, c, deltaSpots + get(r, c).getSpots(), player);
    }

    /** Add DELTASPOTS spots of color PLAYER to square #N,
     *  updating counts of numbers of squares of each color. */
    private void simpleAdd(Side player, int n, int deltaSpots) {
        internalSet(n, deltaSpots + get(n).getSpots(), player);
    }

    /** Used in jump to keep track of squares needing processing.  Allocated
     *  here to cut down on allocations. */
    private final ArrayDeque<Integer> _workQueue = new ArrayDeque<>();


    /** Do all jumping on this board, assuming that initially, S is the only
     *  square that might be over-full. */
    private void jump(int S) {
        int spots = get(S).getSpots();
        int neighbours = this.neighbors(S);
        Side player = get(S).getSide();
        int r = row(S);
        int c = col(S);
        if (spots > neighbours) {
            this.set(r, c, 1, player);
            if (exists(r, c + 1)) {
                addSpot(player, sqNum(r, c + 1));
            }
            if (exists(r, c - 1)) {
                addSpot(player, sqNum(r, c - 1));
            }
            if (exists(r + 1, c)) {
                addSpot(player, sqNum(r + 1, c));
            }
            if (exists(r - 1, c)) {
                addSpot(player, sqNum(r - 1, c));
            }
        }
    }

    /** Returns my dumped representation. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===\n");
        for (int r = 1; r <= size(); r++) {
            out.format("    ");
            for (int c = 1; c <= size(); c++) {
                Side player = get(sqNum(r, c)).getSide();
                int spots = get(sqNum(r, c)).getSpots();
                switch (player) {
                case RED:
                    out.format("%dr ", spots);
                    break;
                case BLUE:
                    out.format("%db ", spots);
                    break;
                case WHITE:
                    out.format("%d- ", spots);
                    break;
                default:
                    System.out.print("Error");
                    break;
                }
            }
            out.format("\n");
        }
        out.format("===");
        return out.toString();
    }

    /** Returns an external rendition of me, suitable for human-readable
     *  textual display, with row and column numbers.  This is distinct
     *  from the dumped representation (returned by toString). */
    public String toDisplayString() {
        String[] lines = toString().trim().split("\\R");
        Formatter out = new Formatter();
        for (int i = 1; i + 1 < lines.length; i += 1) {
            out.format("%2d %s%n", i, lines[i].trim());
        }
        out.format("  ");
        for (int i = 1; i <= size(); i += 1) {
            out.format("%3d", i);
        }
        return out.toString();
    }

    /** Returns the number of neighbors of the square at row R, column C. */
    int neighbors(int r, int c) {
        int size = size();
        int n;
        n = 0;
        if (r > 1) {
            n += 1;
        }
        if (c > 1) {
            n += 1;
        }
        if (r < size) {
            n += 1;
        }
        if (c < size) {
            n += 1;
        }
        return n;
    }

    /** Returns the number of neighbors of square #N. */
    int neighbors(int n) {
        return neighbors(row(n), col(n));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board)) {
            return false;
        } else {
            Board B = (Board) obj;
            return (B._board == this._board
                    && B._history == this._history
                    && B._numMoves == this._numMoves);
        }
    }

    @Override
    public int hashCode() {
        return numPieces();
    }

    /** Set my notifier to NOTIFY. */
    public void setNotifier(Consumer<Board> notify) {
        _notifier = notify;
        announce();
    }

    /** Take any action that has been set for a change in my state. */
    private void announce() {
        _notifier.accept(this);
    }

    /** A notifier that does nothing. */
    private static final Consumer<Board> NOP = (s) -> { };

    /** A read-only version of this Board. */
    private ConstantBoard _readonlyBoard;

    /** Use _notifier.accept(B) to announce changes to this board. */
    private Consumer<Board> _notifier;

    /** _board. */
    private Square[][] _board;

    /** _history. */
    private Stack<Board> _history = new Stack<Board>();

    /** _numMoves. */
    private int _numMoves = 0;

}
