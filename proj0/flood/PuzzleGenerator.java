package flood;

import java.util.Random;

import static flood.Utils.*;

/** A creator of random Flood puzzles.
 *  @author P. N. Hilfinger
 */
class PuzzleGenerator implements PuzzleSource {

    /** A new PuzzleGenerator whose random-number source is seeded
     *  with SEED. */
    PuzzleGenerator(long seed) {
        _random = new Random(seed);
    }

    @Override
    public Model getPuzzle(int width, int height, int ncolors, int extra) {
        int[][] initial = new int[height][width];
        Model model = new Model(initial, ncolors);
        model.setLimit(Solver.movesNeeded(model) + extra);
        return model;
    }


    @Override
    public void setSeed(long seed) {
        _random.setSeed(seed);
    }

    /** My PNRG. */
    private Random _random;

}
