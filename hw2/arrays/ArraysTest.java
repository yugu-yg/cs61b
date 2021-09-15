package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class ArraysTest {
    /** FIXME
     */
    @Test
    public void catenateTest() {
        assertArrayEquals(new int[] {1, 2, 3, 4},
                Arrays.catenate(new int[] {1, 2}, new int[] {3, 4}));
        assertArrayEquals(new int[] {},
                Arrays.catenate(new int[] {}, new int[] {}));
    }

    @Test
    public void removeTest() {
        assertArrayEquals(new int[] {0,3},
                Arrays.remove(new int[] {0, 1, 2, 3}, 2, 2));
    }

    @Test
    public void naturalRunsTest() {
        assertArrayEquals(new int[][] {{1, 3, 7}, {5}, {4, 6, 9, 10}},
                Arrays.naturalRuns(new int[] {1, 3, 7, 5, 4, 6, 9, 10}));
        assertArrayEquals(new int[][] {},
                Arrays.naturalRuns(new int[] {}));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
