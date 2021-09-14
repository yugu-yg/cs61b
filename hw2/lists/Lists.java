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
            IntList L1 = new IntList(L.head, null);
            IntListList LL = new IntListList(L1, null);
            while (L.head < L.tail.head && L.tail.tail != null) {
                L1.tail = L.tail;
                L = L.tail;
            }
            L1.tail = null;
            LL.tail = naturalRuns(L.tail);
            return LL;
        }
    }
}
