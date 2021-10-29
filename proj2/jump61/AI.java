package jump61;

import java.util.ArrayList;
import java.util.Random;

import static jump61.Side.*;

/**
 * An automated Player.
 *
 * @author P. N. Hilfinger
 */
class AI extends Player {

    /**
     * A new player of GAME initially COLOR that chooses moves automatically.
     * SEED provides a random-number seed used for choosing moves.
     */
    AI(Game game, Side color, long seed) {
        super(game, color);
        _random = new Random(seed);
    }

    @Override
    String getMove() {
        Board board = getGame().getBoard();

        assert getSide() == board.whoseMove();
        int choice = searchForMove();
        getGame().reportMove(board.row(choice), board.col(choice));
        return String.format("%d %d", board.row(choice), board.col(choice));
    }

    /**
     * Return a move after searching the game tree to DEPTH>0 moves
     * from the current position. Assumes the game is not over.
     */
    private int searchForMove() {
        Board work = new Board(getBoard());
        assert getSide() == work.whoseMove();
        ArrayList<Integer> moves = new ArrayList<Integer>();
        minMax(new Board(getBoard()), 5,  -_winningValue, moves);

        int r;
        int c;
        ArrayList<int[]> possibleMoves;
        if (moves.size() == 0) {
            possibleMoves = new ArrayList<int[]>();
            for (int i = 1; i <= this.getBoard().size(); i++) {
                for (int j = 1; j <= this.getBoard().size(); j++) {
                    if (this.getBoard().isLegal(this.getSide(), i, j)) {
                        possibleMoves.add(new int[]{i, j});
                    }
                }
            }
            r = possibleMoves.get(0)[0];
            c = possibleMoves.get(0)[1];
        } else {
            r = this.getBoard().row(moves.get(0));
            c = this.getBoard().col(moves.get(0));
        }
        return getBoard().sqNum(r, c);
    }

    /**
     * @param board this is board of table
     * @param moves this is moves
     * @param depth this is depth
     * @param alpha this is alpha
     * @return bestSoFar this is the score
     */
    private int minMax(Board board, int depth, int alpha,
                       ArrayList<Integer> moves) {
        Side p = getSide();
        if (board.getWinner() != null) {
            if (board.getWinner().equals(p)) {
                return Integer.MAX_VALUE;
            } else {
                return -Integer.MAX_VALUE;
            }
        }

        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        for (int r = 1; r <= this.getBoard().size(); r++) {
            for (int c = 1; c <= this.getBoard().size(); c++) {
                if (board.isLegal(p, r, c)) {
                    possibleMoves.add(new int[]{r, c});
                }
            }
        }

        if (depth == 0) {
            int bestEval = -Integer.MAX_VALUE;
            for (int[] move : possibleMoves) {
                Board moveBoard = new Board(board);
                moveBoard.addSpot(p, move[0], move[1]);
                int heuristic = this.staticEval(moveBoard);
                if (heuristic > bestEval) {
                    bestEval = heuristic;
                    moves.clear();
                    moves.add(board.sqNum(move[0], move[1]));
                }
            }
            return bestEval;
        }
        int bestSoFar = -Integer.MAX_VALUE;

        for (int[] move : possibleMoves) {
            Board newBoard = new Board(board);
            newBoard.addSpot(p, move[0], move[1]);
            ArrayList<Integer> x = new ArrayList<Integer>();
            int response = minMax(newBoard, depth - 1, -bestSoFar, x);
            if (-response > bestSoFar) {
                moves.clear();
                moves.add(this.getBoard().sqNum(move[0], move[1]));
                bestSoFar = -response;
                if (-response >= alpha) {
                    break;
                }
            }
        }
        return bestSoFar;
    }

    /**
     * Return a heuristic estimate of the value of board position B.
     * Use WINNINGVALUE to indicate a win for Red and -WINNINGVALUE to
     * indicate a win for Blue.
     */
    private int staticEval(Board b) {
        int myScore = b.numOfSide(getSide());
        int oppScore = b.numOfSide(getSide().opposite());
        return myScore - oppScore;
    }

    /**
     * A random-number generator used for move selection.
     */
    private Random _random;

    /**
     * Define winning value.
     */
    private static int _winningValue = Integer.MAX_VALUE;
}
