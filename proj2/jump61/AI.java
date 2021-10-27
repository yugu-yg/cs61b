// This file contains definitions for an OPTIONAL part of your project.  If you
// choose not to do the optional point, you can delete this file from your
// project.

// This file contains a SUGGESTION for the structure of your program.  You
// may change any of it, or add additional files to this directory (package),
// as long as you conform to the project specification.

// Comments that start with "//" are intended to be removed from your
// solutions.
package jump61;

import afu.org.checkerframework.checker.igj.qual.I;

import java.util.ArrayList;
import java.util.Random;

import static jump61.Side.*;

/** An automated Player.
 *  @author P. N. Hilfinger
 */
class AI extends Player {

    /** A new player of GAME initially COLOR that chooses moves automatically.
     *  SEED provides a random-number seed used for choosing moves.
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

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private int searchForMove() {
        Board work = new Board(getBoard());
        assert getSide() == work.whoseMove();
        if (getSide() == RED) {
            minMax(getBoard(), 5, true, 1, -_winningValue, _winningValue);
        } else {
            minMax(getBoard(), 5, true, -1, -_winningValue, _winningValue);
        }
        return _foundMove;
    }


    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int minMax(Board board, int depth, boolean saveMove,
                       int sense, int alpha, int beta) {
        if (board.getWinner() != null) {
            if (sense == 1) {
                return _winningValue;
            } else {
                return -_winningValue;
            }
        }

        if (depth == 0) {
            return staticEval(getBoard());
        }

        ArrayList<Integer> possibleMoves = possibleMove();
        if (sense == 1) {
            int maxScore = -_winningValue;
            for (int move : possibleMoves) {
                Board copyBoard = new Board(board);
                copyBoard.addSpot(getSide(), move);
                int opponent = minMax(copyBoard, depth - 1, false, -1, alpha, beta);
                copyBoard.undo();
                if (-opponent > alpha) {
                    alpha = -opponent;
                }
                if (-opponent >= beta) {
                    return maxScore;
                }
                if (-opponent > maxScore) {
                    maxScore = -opponent;
                    if (saveMove) {
                        _foundMove = move;
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = _winningValue;
            for (int move : possibleMoves) {
                Board copyBoard = new Board(board);
                copyBoard.addSpot(getSide(), move);
                int opponent = minMax(copyBoard, depth - 1, false, 1, alpha, beta);
                copyBoard.undo();
                if (-opponent <= alpha) {
                    return minScore;
                }
                if (-opponent < beta) {
                    beta = -opponent;
                }
                if (-opponent < minScore) {
                    minScore = -opponent;
                    if (saveMove) {
                        _foundMove = move;
                    }
                }
            }
            return minScore;
        }
    }

    /** Return a heuristic estimate of the value of board position B.
     *  Use WINNINGVALUE to indicate a win for Red and -WINNINGVALUE to
     *  indicate a win for Blue. */
    private int staticEval(Board b) {
        if (b.getWinner()== RED) {
            return _winningValue;
        }
        if (b.getWinner()== BLUE) {
            return -_winningValue;
        }

        int myScore = b.numOfSide(getSide());
        int oppScore = b.numOfSide(getSide().opposite());
        return myScore - oppScore;
    }

    private ArrayList<Integer> possibleMove() {
        ArrayList<Integer> possibleMove = new ArrayList<>();
        for (int n = 0; n < getBoard().size() * getBoard().size(); n++) {
            if (getBoard().isLegal(getSide(), n)) {
                possibleMove.add(n);
            }
        }
        return possibleMove;
    }
    /** A random-number generator used for move selection. */
    private Random _random;

    /** Used to convey moves discovered by minMax. */
    private int _foundMove;

    private final static int _winningValue = Integer.MAX_VALUE;
}
