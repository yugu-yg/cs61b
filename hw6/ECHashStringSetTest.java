import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class ECHashStringSetTest  {

    @Test
    public void testECStringSet() {
        ECHashStringSet tester = new ECHashStringSet();
        tester.put("a");
        tester.put("b");
        tester.put("c");

        assertTrue(tester.contains("a"));

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("tst");
        System.out.println(tester.asList());
    }
}
