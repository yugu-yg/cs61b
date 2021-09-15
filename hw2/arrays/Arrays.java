package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

import java.util.ArrayList;

/** Array utilities.
 *  @author
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        /* *Replace this body with the solution. */
        int [] result = new int[A.length + B.length];
        for (int i = 0; i < A.length; i++){
            result[i] = A[i];
        }
        for (int j= 0; j < B.length; j++){
            result[A.length + j] = B[j];
        }
        return result;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. If the start + len is out of bounds for our array, you
     *  can return null.
     *  Example: if A is [0, 1, 2, 3] and start is 1 and len is 2, the
     *  result should be [0, 3]. */
    static int[] remove(int[] A, int start, int len) {
        /* *Replace this body with the solution. */
        int [] result = new int[A.length - len];
        System.arraycopy(A, 0, result, 0, start);
        if ((start + len) > A.length){
            return null;
        }
        System.arraycopy(A, start + len, result, start, A.length - len - start);
        return result;
    }

    /* C3. */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        /* *Replace this body with the solution. */
        if (A.length == 0){
            return new int[][] {};
        }
        else if(A.length == 1){
            return new int[][] {A};
        }

        int group = 0;
        for(int i = 0; i<A.length; i++){
            if (A[i] >= A[i + 1]){
                group += 1;
            }
        }
        int [][] result = new int [group + 1][];

        int last = 0;
        int index = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= A[i + 1]) {
                result[index] = Utils.subarray(A, last, i - last);
                index += 1;
                last = i;
            }
        }
        if (index != result.length) {
            result[index] = Utils.subarray(A, last, A.length - last);
        }

        return result;
    }
}
