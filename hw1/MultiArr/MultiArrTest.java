import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        //TODO: Your code here!
        int[][] arr = {{ 1, 0}, { 0, 0}};
        assertEquals(1,MultiArr.maxValue(arr));
    }

    @Test
    public void testAllRowSums() {
        //TODO: Your code here!
        int[][] arr =  { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int [] expected = {6,15,24};
        assertArrayEquals(expected, MultiArr.allRowSums(arr));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
