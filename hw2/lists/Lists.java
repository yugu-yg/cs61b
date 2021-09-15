package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author
 */
class Lists {

    /* B. */
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntListList naturalRuns(IntList L) {
        /* *Replace this body with the solution. */
        if (L == null) {
            return null;
        }else if (L.tail == null){
            return new IntListList(L,null);
        }
        else {
            IntList L_move = L;
            IntList L0 = L;
            while (L_move.tail.head > L_move.head){
                L_move = L_move.tail;
            }
            L = L.tail;
            L_move.tail = null;
            return new IntListList(L0, naturalRuns(L));
        }
    }
}
